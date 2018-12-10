package com.ing.baker.tutorials.spaghetti;

import akka.actor.ActorSystem;
import com.google.common.collect.ImmutableList;
import com.ing.baker.compiler.RecipeCompiler;
import com.ing.baker.il.CompiledRecipe;
import com.ing.baker.recipe.javadsl.Recipe;
import com.ing.baker.runtime.java_api.EventList;
import com.ing.baker.runtime.java_api.JBaker;
import com.ing.baker.tutorials.spaghetti.interactions.*;
import com.ing.baker.tutorials.spaghetti.interactions.events.BoilPastaEvents.BoiledPasta;
import com.ing.baker.tutorials.spaghetti.interactions.events.ChopCarrotEvents.CarrotChopped;
import com.ing.baker.tutorials.spaghetti.interactions.events.ChopOnionEvents.OnionChopped;
import com.ing.baker.tutorials.spaghetti.interactions.events.CreateRaguEvents.RaguCreated;
import com.ing.baker.tutorials.spaghetti.interactions.events.FryMeatEvents.MeatFried;
import com.ing.baker.tutorials.spaghetti.interactions.events.FryVegetablesEvents.VegetablesFried;
import com.ing.baker.tutorials.spaghetti.interactions.events.GatheredKitchenware;
import com.ing.baker.tutorials.spaghetti.interactions.events.ServeSpaghettiEvents.HappyCustomer;
import com.ing.baker.tutorials.spaghetti.interactions.events.ShoppedGroceries;
import com.ing.baker.types.Value;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class SpaghettiRecipeTest {
    private final Recipe recipe = SpaghettiRecipe.getRecipe();

    private final BoilPasta boilPastaMock = mock(BoilPasta.class);
    private final ChopCarrot chopCarrotMock = mock(ChopCarrot.class);
    private final ChopOnion chopOnionMock = mock(ChopOnion.class);
    private final CreateRagu createRaguMock = mock(CreateRagu.class);
    private final FryMeat fryMeatMock = mock(FryMeat.class);
    private final FryVegetables fryVegetablesMock = mock(FryVegetables.class);
    private final ServeSpaghetti serveSpaghettiMock = mock(ServeSpaghetti.class);

    //Baker spins an actor system based on AKKA under the hood
    private ActorSystem testActorSystem = ActorSystem.create("Spaghetti");
    private JBaker baker = new JBaker(testActorSystem);

    //Baker can run multiple recipes at the same time, each recipe gets a unique recipeId
    private String recipeId;

    //happy flow mock data
    private final int alDentePasta = 1;
    private final int choppedCarrot = 1;
    private final int choppedOnion = 1;
    private final int ragu = 1;
    private final int friedMeat = 1;
    private final int friedVegetables = 1;
    private final int friedMeatAndVegetables = 1;
    private final int onion = 1;
    private final int carrot = 1;
    private final int mincedMeat = 1;
    private final int pasta = 1;
    private final int tomatoPaste = 1;
    private final int wine = 100;
    private final int herbs = 1;

    private final String knife = "Very sharp knife";
    private final String fryingPan = "Awesome frying pan";
    private final String cookingPod = "Huge cooking pod";

    //the good thing about this is that no actual implementations are needed
    //the complete flow can be verified that it will work, speeds up the validation of orchestration logic
    private List<Object> mockImplementations = ImmutableList.<Object>of(
            boilPastaMock,
            chopCarrotMock,
            chopOnionMock,
            createRaguMock,
            fryMeatMock,
            fryVegetablesMock,
            serveSpaghettiMock
    );

    public SpaghettiRecipeTest() {
        CompiledRecipe compiledRecipe = RecipeCompiler.compileRecipe(recipe);
        baker.addImplementations(mockImplementations);
        recipeId = baker.addRecipe(compiledRecipe);
    }

    @Before
    public void setupTest() throws Exception {
        for (Object mockImplementation : mockImplementations) {
            if (mockImplementation instanceof Mock)
                reset(mockImplementation);
        }
    }

    @After
    public void stopActorSystem() throws TimeoutException, InterruptedException {
        Await.ready(testActorSystem.terminate(), Duration.apply(20, TimeUnit.SECONDS));
    }

    @Test
    public void shouldVisualiseTheRecipe() throws IOException {
        CompiledRecipe compiledRecipe = RecipeCompiler.compileRecipe(recipe);
        String visualRecipe = compiledRecipe.getRecipeVisualization();
        saveVisualizationAsSvg(visualRecipe);
        System.out.println("\n\n" + visualRecipe + "\n\n");
    }

    @Test
    //ingredients not provided will cause validation errors for example
    public void shouldHaveNoValidationErrors() {
        CompiledRecipe compileRecipe = RecipeCompiler.compileRecipe(recipe);
        //this is a sound recipe and it's guaranteed that it will work
        Assert.assertEquals(Collections.emptyList(), compileRecipe.getValidationErrors());
    }

    @Test
    //Baker will check that it has implementations of all interactions in the recipe
    public void shouldBakeTheRecipe() throws Exception {
        UUID processId = UUID.randomUUID();

        //create an instance of the recipe (flow) for each customer and give it a unique id
        baker.bake(recipeId, processId);
        Map<String, Value> accumulatedState = baker.getIngredients(processId);
        assert (accumulatedState != null);
    }

    @Test
    //Baker will check that it has implementations of all interactions in the recipe
    public void shouldExecuteHappyFlowCorrectly() throws Exception {
        setupMocks();

        UUID processId = UUID.randomUUID();
        baker.bake(recipeId, processId);

        //blocks the current thread until all interactions that can be called have been executed by Baker
        //this is useful when an underlying system gives information back that must be returned in the response from the API or in unit-tests
        baker.processEvent(processId, new ShoppedGroceries(onion, carrot, mincedMeat, pasta, tomatoPaste, wine, herbs));
        verify(serveSpaghettiMock, never()).apply(alDentePasta, ragu);

        baker.processEvent(processId, new GatheredKitchenware(knife, fryingPan, cookingPod));
        verify(boilPastaMock).apply(cookingPod, pasta);
        verify(chopCarrotMock).apply(carrot, knife);
        verify(serveSpaghettiMock, times(1)).apply(alDentePasta, ragu);

        // You can check which events happened
        EventList events = baker.getEvents(processId);
        Assert.assertTrue(events.hasEventOccurred(HappyCustomer.class));

        // Get all ingredients that are accumulated for a process instance
        Map<String, Value> ingredients = baker.getIngredients(processId);
        int wineIngredient = ingredients.get("wine").as(int.class);
        Assert.assertEquals(wine, wineIngredient);
    }

    private void setupMocks() throws Exception {
        when(boilPastaMock.apply(cookingPod, pasta)).thenReturn(new BoiledPasta(alDentePasta));
        when(chopCarrotMock.apply(carrot, knife)).thenReturn(new CarrotChopped(choppedCarrot));
        when(chopOnionMock.apply(onion, knife)).thenReturn(new OnionChopped(choppedOnion));
        when(createRaguMock.apply(fryingPan, friedMeatAndVegetables, tomatoPaste, wine, herbs)).thenReturn(new RaguCreated(ragu));
        when(fryMeatMock.apply(fryingPan, mincedMeat, friedVegetables)).thenReturn(new MeatFried(friedMeat));
        when(fryVegetablesMock.apply(fryingPan, carrot, onion)).thenReturn(new VegetablesFried(friedVegetables));
        when(serveSpaghettiMock.apply(alDentePasta, ragu)).thenReturn(new HappyCustomer());
    }

    private void saveVisualizationAsSvg(final String dotGraph) throws IOException {
        final File file = new File("./target/" + "spaghetti-recipe.svg");
        final MutableGraph g = Parser.read(dotGraph);
        //try Format.SVG for a vector format, better for printed recipes
        Graphviz.fromGraph(g).render(Format.SVG).toFile(file);
        System.out.println("Exported graph here: " + file.getAbsolutePath());
    }
}
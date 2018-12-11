<?php

error_reporting(E_ALL);
ini_set('display_errors',true);

$maxIngredient = 100;
$maxInteraction = 60;

$fromBindingCount = 150;
$towardsBindingCount = 150;

$usedIngredients = [];
$usedInteractions = [];

$ingredients = [];
for ($i = 0; $i <= $maxIngredient; $i++) {
    $ingredients[$i] = '    ingredient'.$i.' [shape = circle, fontcolor = "#FF6200", color = "#FF6200", style = filled]';
}

$interactions = [];
for ($i = 0; $i <= $maxInteraction; $i++) {
    $interactions[$i] = '    interaction'.$i.' [shape = rect, penwidth = 2, margin = 0.5, fontcolor = "#525199", color = "#525199", style = "rounded, filled"]';
}

$fromBindings = [];
for ($i = 0; $i < $fromBindingCount; $i++) {
    $ingredientId = rand(0, $maxIngredient);
    $interactionId = rand(0, $maxInteraction);
    $usedIngredients[$ingredientId] = true;
    $usedInteractions[$interactionId] = true;
    $fromBindings[] = '    ingredient'.$ingredientId.' -> interaction'.$interactionId.' [penwidth=8]';
}

$towardsBindings = [];
for ($i = 0; $i < $towardsBindingCount; $i++) {
    $ingredientId = rand(0, $maxIngredient);
    $interactionId = rand(0, $maxInteraction);
    $usedIngredients[$ingredientId] = true;
    $usedInteractions[$interactionId] = true;
    $towardsBindings[] = '    interaction'.$interactionId.' -> ingredient'.$ingredientId.' [penwidth=8]';
}

$ingredients = array_intersect_key($ingredients, $usedIngredients);
$interactions = array_intersect_key($interactions, $usedInteractions);

$ingredients = implode("\n", $ingredients);
$interactions = implode("\n", $interactions);
$fromBindings = implode("\n", $fromBindings);
$towardsBindings = implode("\n", $towardsBindings);

echo '


digraph {
	node [fontcolor = white, fontsize = 22, fontname = "ING Me"]
	pad = 0.2

'.$ingredients.'
    
'.$interactions.'

	HappyCustomer [shape = diamond, margin = 0.3, color = "#767676", style = "rounded, filled"]
	SadCustomer [shape = diamond, margin = 0.3, color = "#767676", style = "rounded, filled"]

'.$fromBindings.'
'.$towardsBindings.'

    interaction0 -> HappyCustomer 
    interaction0 -> SadCustomer 
}

';

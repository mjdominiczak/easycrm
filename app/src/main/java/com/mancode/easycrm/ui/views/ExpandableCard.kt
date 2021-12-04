package com.mancode.easycrm.ui.views

import androidx.compose.animation.*
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandableCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    additionalContent: @Composable () -> Unit
) {
    var currentState by remember { mutableStateOf(CardState.Collapsed) }
    val transition = updateTransition(currentState)
    val cardElevation by transition.animateDp(label = "CardElevation") { state ->
        when (state) {
            CardState.Expanded -> 12.dp
            CardState.Collapsed -> 4.dp
        }
    }
    Card(
        onClick = {
            currentState =
                if (currentState == CardState.Collapsed) CardState.Expanded else CardState.Collapsed
        },
        elevation = cardElevation
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            content()
            ExpandableContent(visible = currentState == CardState.Expanded) {
                additionalContent()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableContent(
    visible: Boolean = true,
    content: @Composable () -> Unit
) {
    val enterExpand = remember { expandVertically() }
    val exitShrink = remember { shrinkVertically() }
    AnimatedVisibility(
        visible = visible,
        enter = enterExpand + fadeIn(),
        exit = exitShrink + fadeOut()
    ) {
        content()
    }
}

private enum class CardState {
    Expanded,
    Collapsed
}
package com.example.budgetlyapp.features.expense.presentation.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgetlyapp.common.domain.models.TagModel
import com.example.budgetlyapp.ui.theme.AppTheme
import android.graphics.Color as ColorAndroid

@SuppressLint("DiscouragedApi")
@Composable
fun CategoryComponent(tagModel: TagModel) {
    var categorySelected by remember { mutableStateOf(false) }
    val widthAnimation by animateDpAsState(
        targetValue = if (!categorySelected) 48.dp else 50.dp,
        label = "widthAnimation"
    )
    val heightAnimation by animateDpAsState(
        targetValue = if (!categorySelected) 48.dp else 80.dp,
        label = "heightAnimation"
    )
    val context = LocalContext.current
    val icon = context.resources.getIdentifier(tagModel.iconId, "drawable", context.packageName)
    val color = Color(ColorAndroid.parseColor(tagModel.color))
    val text = context.resources.getString(
        context.resources.getIdentifier(
            tagModel.tagNameId,
            "string",
            context.packageName
        )
    )

    Box {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .width(50.dp)
                .height(80.dp)
                .clickable {
                    categorySelected = !categorySelected
                }
        ) {
            Box(
                modifier = Modifier
                    .clip(if (categorySelected) RoundedCornerShape(8.dp) else CircleShape)
                    .background(color)
                    .width(widthAnimation)
                    .height(heightAnimation)
            ) {

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 2.dp, end = 2.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }

                Text(
                    text,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = if (!categorySelected) MaterialTheme.colorScheme.onSurface else Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, apiLevel = 34)
@Composable
fun CategoryComponentPreview() {
    AppTheme {
        CategoryComponent(
            TagModel(1, "tag_saving", "#0000ff", "ic_savings_category")
        )
    }
}
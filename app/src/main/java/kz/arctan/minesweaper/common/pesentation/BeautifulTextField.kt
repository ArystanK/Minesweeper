package kz.arctan.minesweaper.common.pesentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BeautifulTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIconData: Pair<ImageVector, String>? = null,
    trailingIconData: Pair<ImageVector, String>? = null,
    placeholder: String? = null,
    obscureText: Boolean = false
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(20),
        textStyle = TextStyle.Default.copy(
            textDecoration = TextDecoration.None,
        ),
        singleLine = true,
        leadingIcon = generateIcon(leadingIconData),
        label = generatePlaceholder(placeholder, value),
        trailingIcon = generateIcon(trailingIconData),
        visualTransformation = if (obscureText) PasswordVisualTransformation() else VisualTransformation.None
    )
}

fun generateIcon(iconData: Pair<ImageVector, String>?): @Composable (() -> Unit)? {
    if (iconData == null) return null
    return { Icon(imageVector = iconData.first, contentDescription = iconData.second) }
}

fun generatePlaceholder(placeholder: String?, value: String): @Composable (() -> Unit)? {
    if (placeholder == null) return null
    return { if (value.isBlank()) Text(placeholder) }
}

@Preview
@Composable
fun BeautifulTextFieldPreview() {
    Box(
        modifier = Modifier
            .width(500.dp)
            .height(200.dp)
    ) {
        var value by remember { mutableStateOf("Hello") }
        BeautifulTextField(
            value = value,
            onValueChange = { value = it },
            leadingIconData = Icons.Default.Person to "username",
            placeholder = "Username"
        )
    }
}

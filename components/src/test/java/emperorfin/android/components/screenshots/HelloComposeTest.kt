/*
 * Copyright 2023 Francis Nwokelo (emperorfin)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package emperorfin.android.components.screenshots

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test


/*
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 18th May, 2023.
 */


class HelloComposeTest {
    @get:Rule
    val paparazzi = Paparazzi()

//    @Test
//    fun compose() {
//        paparazzi.snapshot { HelloPaparazzi() }
//    }

    @Test
    fun screenshot_Hello_Paparazzi() {
        paparazzi.snapshot { HelloPaparazzi() }
    }
}

@Suppress("TestFunctionName")
@Composable
fun HelloPaparazzi() {
    val text = "Hello, Paparazzi"
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Text(text)
        Text(text, style = TextStyle(fontFamily = FontFamily.Cursive))
        Text(
            text = text,
            style = TextStyle(textDecoration = TextDecoration.LineThrough)
        )
        Text(
            text = text,
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )
        Text(
            text = text,
            style = TextStyle(
                textDecoration = TextDecoration.combine(
                    listOf(
                        TextDecoration.Underline,
                        TextDecoration.LineThrough
                    )
                ),
                fontWeight = FontWeight.Bold
            )
        )
    }
}
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

package emperorfin.android.components.ui.res

import android.content.Context
import androidx.annotation.FractionRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: November, 2022.
 */


/**
 * Load fraction resource.
 *
 * Since there doesn't seem to be a float/double resource, the fraction resource can be used for
 * this purpose.
 *
 * @param id the resource identifier
 * @return the float associated with the resource
 */
@Composable
@ReadOnlyComposable
fun fractionResource(@FractionRes id: Int): Float {

    val context: Context = LocalContext.current

    return context.resources.getFraction(id, ONE, ONE)

}

private const val ONE: Int = 1
package emperorfin.android.composeemailauthentication.ui.res

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
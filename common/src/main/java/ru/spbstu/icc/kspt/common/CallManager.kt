package ru.spbstu.icc.kspt.common

import android.app.Activity
import android.content.Intent
import java.util.concurrent.atomic.AtomicInteger

/**
 * Type parameter T must be not nullable
 */
class CallManager<T>(
        private val name: String,
        private val activityToCall: Class<out Activity>,
        private val activity: Activity
) {

    private val callbacks = HashMap<Int, (T) -> Unit>()

    private val idGenerator = AtomicInteger()

    fun call(requestCode: Int, callback: (T) -> Unit, configure: Intent.() -> Unit) {
        val id = idGenerator.incrementAndGet()
        callbacks[id] = callback
        val intent = Intent(activity, activityToCall)
        intent.putExtra(CALLBACK_ID, id)
        intent.configure()
        activity.setResult(Activity.RESULT_OK, intent)
        activity.startActivityForResult(intent, requestCode)
    }

    fun onActivityResult(data: Intent) {
        val extras = data.extras ?: return
        val result = extras.get(name) ?: return
        val id = data.getExtra<Int>(CALLBACK_ID)
        @Suppress("UNCHECKED_CAST")
        callbacks.remove(id)?.invoke(result as T)
    }

    companion object {
        const val CALLBACK_ID = "ru.spbstu.icc.kspt.storage.ExternalStorageManager.CALLBACK_ID"
    }
}
package com.possible.demo

class GithubDelegate {

    val layoutId = R.layout.github_username_item
    val dataClass = GithubLoginDataContainer::class.java

    fun canHandleData(data: Any?) : Boolean =
        data?.javaClass?.isAssignableFrom(dataClass) == true

}

/*package com.jetblue.JetBlueAndroid.features.book.delegate

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jetblue.JetBlueAndroid.BR
import com.jetblue.JetBlueAndroid.features.book.container.ClickableDataContainer
import com.jetblue.JetBlueAndroid.features.book.container.DataContainer
import com.jetblue.JetBlueAndroid.utilities.adapter.AdapterDelegatesManager

abstract class BaseDelegate<B : ViewDataBinding, D : DataContainer> : AdapterDelegatesManager.DelegateItem() {

    protected abstract val layoutId: Int
    protected abstract val dataClass: Class<D>

    override fun canHandleData(data: Any?): Boolean =
            data?.javaClass?.isAssignableFrom(dataClass) == true

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<B>(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
        )
        return ViewHolder<B, D>(binding)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, data: Any) {
        (viewHolder as? ViewHolder<B, D>)?.bind(data as D)
    }

    class ViewHolder<B : ViewDataBinding, in D : DataContainer>(
            private val binding: B
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: D) {
            binding.setVariable(BR.data, data)
            if (data is ClickableDataContainer<*>) {
                binding.root.setOnClickListener {
                    data.onClick()
                }
            }
            binding.executePendingBindings()
        }
    }
}
*/ /* */
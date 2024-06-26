package com.e_book.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity() {

    private var mViewDataBinding: T? = null
    private var mViewModel: V? = null

    abstract fun getBindingVariable(): Int


    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V?

    fun getViewDataBinding(): T {
        return mViewDataBinding!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = if (mViewModel == null)
            getViewModel() else mViewModel
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.executePendingBindings()
    }
}
package com.findly.application.main.recognised

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.findly.R
import com.findly.application.base.BaseFragment
import com.findly.application.main.unrecognised.UnrecognisedAdapter
import com.findly.application.postDetail.PostDetailsActivity
import com.findly.data.firebase.Database
import com.findly.data.firebase.model.Post
import kotlinx.android.synthetic.main.fragment_unrecognised.*

/**
 * Created by Wojdor on 20.04.2018.
 */

class UnrecognisedFragment : BaseFragment(), UnrecognisedContract.View {

    companion object {
        private const val NUMBER_OF_COLUMN = 3
    }

    var searchPhrase: String = ""
    var posts = mutableListOf<Post>()
    var presenter: UnrecognisedContract.Presenter = UnrecognisedPresenter()
    private val adapter = UnrecognisedAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_unrecognised, container, false)
    }

    private fun setupPostsRv() {
        fragmentUnrecognisedPostsRv.layoutManager = GridLayoutManager(context, NUMBER_OF_COLUMN) as RecyclerView.LayoutManager?
        fragmentUnrecognisedPostsRv.adapter = adapter
        adapter.onItemClick {
            startActivity(Intent(context, PostDetailsActivity::class.java).apply {
                putExtra("post", it)
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        setupPostsRv()
        Database.getPosts {
            adapter.updatePosts(it.filter { it.description.contains(searchPhrase.toRegex()) })
            posts = it
        }
        fragmentUnrecognisedSearchEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence, p1: Int, p2: Int, p3: Int) {
                searchPhrase = text.toString()
                adapter.updatePosts(posts.filter { it.description.contains(text.toString().toRegex()) })
            }

        })
    }
}

package com.findly.application.postDetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.findly.R
import com.findly.application.GlideApp
import com.findly.data.firebase.Database
import com.findly.data.firebase.model.Comment
import com.findly.data.firebase.model.Post
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_post_details.*

class PostDetailsActivity : AppCompatActivity() {
    var offerId: String = ""
    lateinit var post: Post
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)
        post = (intent.getSerializableExtra("post") as Post)
        with(activityPostDetailsCommentsRv)
        {
            layoutManager = LinearLayoutManager(this@PostDetailsActivity, RecyclerView.VERTICAL, false)
            adapter = PostDetailsCommentsAdapter()
        }
        Database.getPost(post.key) {
            updateComments(post)
        }
        setupLayout()
    }

    private fun updateComments(post: Post) {
        (activityPostDetailsCommentsRv.adapter as? PostDetailsCommentsAdapter)?.updateComments(
                post.comments.map {
                    it.value
                }
        )

    }

    private fun setupLayout() {
        GlideApp.with(this).load(post.imageUrl).into(activityPostDetailImage)
        activityPostDetailsUsername.text = post.userName
        activityPostDetailsDescription.text = post.description
        activityPostDetailsAttachment.setOnClickListener { startAttachmentDialog() }
        activityPostDetailsAddComment.setOnClickListener { addComment() }

    }

    private fun addComment() {
        Database.addComment(Comment().apply {
            userName = "Patryk"
            idAuction = offerId
            isAccepted = false
        }, post.key, OnCompleteListener {
            Log.e("dodano", "komentarz")
        })
    }

    private fun startAttachmentDialog() {
        var dialog = PostDetailsAttachmentDialog.newInstance()
        dialog.show(fragmentManager, dialog.javaClass.name)
    }

}

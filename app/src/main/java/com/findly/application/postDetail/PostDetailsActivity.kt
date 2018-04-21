package com.findly.application.postDetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.findly.R
import com.findly.application.GlideApp
import com.findly.data.firebase.model.Post
import kotlinx.android.synthetic.main.activity_post_details.*

class PostDetailsActivity : AppCompatActivity() {
    lateinit var post: Post
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)
        post = (intent.getSerializableExtra("post") as Post)
        setupLayout()
    }

    private fun setupLayout() {
        GlideApp.with(this).load(post.imageUrl).into(activityPostDetailImage)
        activityPostDetailsUsername.text = post.userName
        activityPostDetailsDescription.text = post.description
        activityPostDetailsAttachment.setOnClickListener { startAttachmentDialog() }

    }

    private fun startAttachmentDialog() {
        var dialog = PostDetailsAttachmentDialog.newInstance()
        dialog.show(fragmentManager, dialog.javaClass.name)
    }
}

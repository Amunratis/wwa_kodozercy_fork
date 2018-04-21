package com.findly.application.results

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.findly.R
import com.findly.application.base.BaseActivity
import com.findly.application.shareImage.ShareImageActivity
import com.findly.application.view.FullScreenLoadingDialog
import com.findly.data.service.response.Offers
import kotlinx.android.synthetic.main.activity_results.*
import java.io.ByteArrayOutputStream

class ResultsActivity : BaseActivity(), ResultsContract.View {
    lateinit var image: Bitmap
    lateinit var loadingDialog: FullScreenLoadingDialog

    companion object {
        private const val NUMBER_OF_COLUMN = 2
    }

    var presenter: ResultsContract.Presenter = ResultsPresenter()
    private val resultsAdapter: ResultsAdapter = ResultsAdapter()
    private val tagsAdapter: TagsAdapter = TagsAdapter()

    var tags = mutableListOf<String>()
    var phrase = ""

    override fun switchLoading(isLoading: Boolean) {
        if (isLoading)
            loadingDialog.show()
        else
            loadingDialog.hide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        loadingDialog = FullScreenLoadingDialog.newProgressDialogInstance(this)
        handleExtras()
        setupListeners()
        setupResultsRv()
        setupTagsRv()
        setupSearchTet()
        setupBackIv()
        presenter.attachView(this)
        presenter.downloadOffers(activityResultsSearchTet.tags.toString())
    }

    private fun setupListeners() {
        activityResultsShareBtn.setOnClickListener { startShareActivity() }
    }

    private fun startShareActivity() {
        Intent(this, ShareImageActivity::class.java).apply {
            putExtra("image", image.createImageFromBitmap())
        }.run { startActivity(this) }
    }

    private fun handleExtras() {
        tags = intent.getStringArrayListExtra("results").toMutableList()
        intent.getStringExtra("image")?.let {
            image = getImageBitmap()
        }
        phrase = tags.component1()
    }

    private fun setupResultsRv() {
        activityResultsOffersRv.setLayoutManager(GridLayoutManager(this, NUMBER_OF_COLUMN))
        activityResultsOffersRv.adapter = resultsAdapter
        resultsAdapter.onItemClick { openOffer(it.url) }
    }

    private fun openOffer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun setupTagsRv() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        activityResultsTagsRv.layoutManager = layoutManager
        activityResultsTagsRv.adapter = tagsAdapter
        tagsAdapter.updateTags(tags)
        tagsAdapter.onItemClick { tag ->
            deleteTagFromTagsRv(tag)
            addTag(tag)
        }
    }

    private fun addTag(tag: String) {
        val tags = activityResultsSearchTet.tags.toTypedArray().toMutableList()
        tags.add(tag)
        activityResultsSearchTet.setTags(tags.toTypedArray())
        presenter.downloadOffers(activityResultsSearchTet.tags.toString())
    }

    private fun deleteTagFromTagsRv(tag: String) {
        tags = tags.filterNot { tag == it }.toMutableList()
        tagsAdapter.updateTags(tags)
    }

    private fun setupSearchTet() {
        var phraseSplited = phrase.split(' ')
        activityResultsSearchTet.setTags(phraseSplited[0])
        tags.addAll(0, phraseSplited)
    }

    private fun setupBackIv() {
        activityResultsBackIv.setOnClickListener { onBackPressed() }
    }

    override fun showOffers(offers: List<Offers>) {
        resultsAdapter.updateOffers(offers)
    }

    private fun getImageBitmap() = BitmapFactory.decodeStream(this
            .openFileInput(intent.getStringExtra("image")))

    fun Bitmap.createImageFromBitmap(): String? {
        var fileName: String? = "myImage"
        try {
            with(ByteArrayOutputStream()) {
                this@createImageFromBitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
                openFileOutput(fileName, Context.MODE_PRIVATE).run {
                    write(this@with.toByteArray())
                    close()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            fileName = null
        }
        return fileName
    }
}

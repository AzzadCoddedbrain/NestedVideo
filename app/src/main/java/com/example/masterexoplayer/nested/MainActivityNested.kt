package com.example.masterexoplayer.nested

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.masterexoplayer.AppPreferences
import com.example.masterexoplayer.Model
import com.example.masterexoplayer.R
import com.example.masterexoplayer.databinding.ActivityMainBinding
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.master.exoplayer.MasterExoPlayerHelper
import com.master.exoplayer.MuteStrategy
import kotlinx.android.synthetic.main.activity_main.*


class MainActivityNested : AppCompatActivity() {

    lateinit var masterExoPlayerHelper: MasterExoPlayerHelper

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val save = AppPreferences


    var isMute: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        masterExoPlayerHelper = MasterExoPlayerHelper(mContext = this,id = R.id.frame,muteStrategy = MuteStrategy.ALL, defaultMute = isMute, autoPlay = true)

        masterExoPlayerHelper.makeLifeCycleAware(this)

        setAdapter()
        masterExoPlayerHelper.attachToRecyclerView(recyclerView)

        masterExoPlayerHelper.getPlayerView().apply {
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        }




    }




    fun setAdapter() {
        val adapter = PostAdapter(getSampleData(), masterExoPlayerHelper,isMute)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


    fun getSampleData(): ArrayList<Model> {
        return arrayListOf<Model>(
            Model().apply {
                title = "Big Buck Bunny"
                sources =
                    "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                thumb =
                    "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
            },
            Model().apply {
                title = "Multiple Video Right Swipe to see"
                sources =
                    "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                thumb =
                    "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
                sourcesList = arrayOf(Model().apply {
                    title = "Big Buck Bunny"
                    sources =
                        "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                    thumb =
                        "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
                },
                    Model().apply {
                        title = "Elephant Dream"
                        sources =
                            "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                        thumb =
                            "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
                        sourcesList = arrayOf()
                    })
            },
            Model().apply {
                title = "Image Only"
                sources =
                    ""
                thumb =
                    "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"

            },
            Model().apply {
                title = "For Bigger Blazes"
                sources =
                    "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                thumb =
                    "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
            },
            Model().apply {
                title = "For Bigger Escape"
                sources =
                    "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                thumb =
                    "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
            },
            Model().apply {
                title = "For Bigger Fun"
                sources =
                    "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                thumb =
                    "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
            },
            Model().apply {
                title = "For Bigger Joyrides"
                sources =
                    "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                thumb =
                    "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
                sourcesList = arrayOf(Model().apply {
                    title = "Big Buck Bunny"
                    sources =
                        "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                    thumb =
                        "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
                },
                    Model().apply {
                        title = "Elephant Dream"
                        sources =
                            "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                        thumb =
                            "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
                        sourcesList = arrayOf()
                    })
            },
            Model().apply {
                title = "For Bigger Meltdowns"
                sources =
                    "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                thumb =
                    "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
            },
            Model().apply {
                title = "Sintel"
                sources =
                    "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                thumb =
                    "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
            },
            Model().apply {
                title = "Subaru Outback On Street And Dirt"
                sources =
                    "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                thumb =
                    "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
            },
            Model().apply {
                title = "Tears of Steel"
                sources =
                    "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                thumb =
                    "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
            },
            Model().apply {
                title = "Volkswagen GTI Review"
                sources =
                    "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                thumb =
                    "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
            },
            Model().apply {
                title = "We Are Going On Bullrun"
                sources =
                    "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                thumb =
                    "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
            },
            Model().apply {
                title = "What care can you get for a grand?"
                sources =
                    "https://media.nojoto.com/content/media/28619628/2023/04/feed/dd80ff4079297899fa055ccc0f764c2b/dd80ff4079297899fa055ccc0f764c2b_default.mp4"
                thumb =
                    "https://www.shutterstock.com/image-photo/word-demo-appearing-behind-torn-260nw-1782295403.jpg"
            }
        )
    }
}

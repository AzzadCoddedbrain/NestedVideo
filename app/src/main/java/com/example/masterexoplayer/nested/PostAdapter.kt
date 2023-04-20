package com.example.masterexoplayer.nested

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.masterexoplayer.Model
import com.example.masterexoplayer.R
import com.example.masterexoplayer.databinding.ItemBinding
import com.example.masterexoplayer.databinding.ItemImageBinding
import com.example.masterexoplayer.databinding.ItemNestedBinding
import com.example.masterexoplayer.databinding.NestedItemPlayerBinding
import com.master.exoplayer.ExoPlayerHelper
import com.master.exoplayer.MasterExoPlayerHelper
import com.simpleadapter.SimpleAdapter
import kotlin.collections.ArrayList


class PostAdapter(val list: ArrayList<Model>, val masterExoPlayerHelper: MasterExoPlayerHelper, isMute: Boolean) : RecyclerView.Adapter<PostAdapter.MyViewHolder>() {

    val TYPE_SINGLE = 1
    val TYPE_MULTIPLE = 2
    val TYPE_IMAGE = 3

    var isVideoMute=isMute

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        when (viewType) {
            TYPE_SINGLE -> {
                return SingleViewHolder(
                    ItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_IMAGE -> {
                return ImageViewHolder(
                    ItemImageBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return MultipleViewHolder(
                    ItemNestedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ).apply {
                    masterExoPlayerHelper.attachToRecyclerView(binding.recyclerView)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list.get(position).sourcesList.isNotEmpty()) {
            TYPE_MULTIPLE
        } else if (list.get(position).sources.isBlank()) {
            TYPE_IMAGE
        } else {
            TYPE_SINGLE
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list.get(holder.adapterPosition)
        holder.onBind(model,isVideoMute)
    }

    abstract class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(model: Model, isVideoMute: Boolean);

    }

    class SingleViewHolder(val binding: ItemBinding) : MyViewHolder(binding.root) {

        override fun onBind(model: Model, isVideoMute: Boolean) {
            binding.text.text = model.title
            binding.frame.url = model.sources
            binding.frame.imageView = binding.image
//            binding.image.load(model.thumb)

            binding.image.load(retriveVideoFrameFromVideo(model.sources))

            binding.frame.isMute = isVideoMute

            // single video
            binding.frame.setOnClickListener {
                val handler = Handler()
                handler.postDelayed({binding.ivVolume.visibility=View.INVISIBLE }, 1000)

                if (binding.frame.isMute) {
                    binding.ivVolume.visibility =View.VISIBLE
                    binding.ivVolume.setImageResource(R.drawable.ic_volume_off)
                } else {
                    binding.ivVolume.setImageResource(R.drawable.ic_volume_on)
                    binding.ivVolume.visibility =View.VISIBLE
                }
                 binding.frame.isMute = !binding.frame.isMute
            }
        }

       fun retriveVideoFrameFromVideo(videoPath: String?): Bitmap? {
            var bitmap: Bitmap? = null
            var mediaMetadataRetriever: MediaMetadataRetriever? = null
            try {
                mediaMetadataRetriever = MediaMetadataRetriever()
                mediaMetadataRetriever.setDataSource(
                    videoPath,
                    HashMap()
                )
                //   mediaMetadataRetriever.setDataSource(videoPath);
                bitmap = mediaMetadataRetriever.frameAtTime
            } catch (e: Exception) {
                e.printStackTrace()
                throw Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.message)
            } finally {
                mediaMetadataRetriever?.release()
            }
            return bitmap
        }
    }

    class ImageViewHolder(val binding: ItemImageBinding) : MyViewHolder(binding.root) {
        override fun onBind(model: Model, isVideoMute: Boolean) {
            binding.text.text = model.title
            binding.image.load(model.thumb)
        }
    }

    class MultipleViewHolder(val binding: ItemNestedBinding) : MyViewHolder(binding.root) {
        val adapter: SimpleAdapter<Model>

        init {
            adapter =
                SimpleAdapter.with<Model, NestedItemPlayerBinding>(R.layout.nested_item_player) { adapterPosition, model, binding ->
                    binding.frame.url = model.sources
                    binding.frame.imageView = binding.image
//                    binding.image.load(model.thumb)
                    binding.image.load(retriveVideoFrameFromVideo(model.sources))

                    binding.frame.setOnClickListener {

                        val handler = Handler()
                        handler.postDelayed({binding.ivVolume.visibility=View.INVISIBLE }, 1000)

                        binding.frame.isMute = !binding.frame.isMute

                        if (binding.frame.isMute) {
                            binding.ivVolume.visibility =View.VISIBLE
                            binding.ivVolume.setImageResource(R.drawable.ic_volume_off)
                        } else {
                            binding.ivVolume.setImageResource(R.drawable.ic_volume_on)
                            binding.ivVolume.visibility =View.VISIBLE
                        }

                    }

                    binding.frame.listener = object : ExoPlayerHelper.Listener {
                        override fun onBuffering(isBuffering: Boolean) {
                            super.onBuffering(isBuffering)
                            binding.ivVolume.visibility = View.INVISIBLE
                        }
                        //Update mute/unmute icon on player ready callback.
                        override fun onPlayerReady() {
                            super.onPlayerReady()
                            binding.ivVolume.visibility = View.VISIBLE
                            if (binding.frame.isMute) {
                                binding.ivVolume.setImageResource(R.drawable.ic_volume_off)
                            } else {
                                binding.ivVolume.setImageResource(R.drawable.ic_volume_on)
                            }
                            binding.frame.isMute = !binding.frame.isMute
                        }

                        override fun onStop() {
                            super.onStop()
                            binding.ivVolume.visibility = View.INVISIBLE
                        }
                    }

                }

            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(binding.recyclerView)
            binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false);
            binding.recyclerView.adapter = adapter



        }

        private fun retriveVideoFrameFromVideo(videoPath: String?): Bitmap? {
            var bitmap: Bitmap? = null
            var mediaMetadataRetriever: MediaMetadataRetriever? = null
            try {
                mediaMetadataRetriever = MediaMetadataRetriever()
                mediaMetadataRetriever.setDataSource(
                    videoPath,
                    HashMap()
                )
                //   mediaMetadataRetriever.setDataSource(videoPath);
                bitmap = mediaMetadataRetriever.frameAtTime
            } catch (e: Exception) {
                e.printStackTrace()
                throw Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.message)
            } finally {
                mediaMetadataRetriever?.release()
            }
            return bitmap
        }

        override fun onBind(model: Model, isVideoMute: Boolean) {
            binding.text.text = model.title
            adapter.clear()
            adapter.addAll(model.sourcesList.toList())
            adapter.notifyDataSetChanged()

        }


    }

}


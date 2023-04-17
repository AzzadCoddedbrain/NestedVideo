package com.example.masterexoplayer.nested

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.withStyledAttributes
import androidx.core.view.ViewCompat.animate
import androidx.core.view.doOnLayout
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
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
import kotlinx.android.synthetic.main.custom_player_controller.view.*
import kotlinx.android.synthetic.main.item_nested.view.*
import kotlinx.android.synthetic.main.nested_item_player.view.*
import kotlinx.coroutines.awaitAll
import java.util.*
import kotlin.collections.ArrayList


class PostAdapter(val list: ArrayList<Model>, val masterExoPlayerHelper: MasterExoPlayerHelper) :
    RecyclerView.Adapter<PostAdapter.MyViewHolder>() {
    val TYPE_SINGLE = 1
    val TYPE_MULTIPLE = 2
    val TYPE_IMAGE = 3
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
                    ItemNestedBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
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
        holder.onBind(model)
    }

    abstract class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(model: Model);
    }

    class SingleViewHolder(val binding: ItemBinding) : MyViewHolder(binding.root) {
        override fun onBind(model: Model) {
            binding.text.text = model.title
            binding.frame.url = model.sources
            binding.frame.imageView = binding.image
            binding.image.load(model.thumb)
        }
    }

    class ImageViewHolder(val binding: ItemImageBinding) : MyViewHolder(binding.root) {
        override fun onBind(model: Model) {
            binding.text.text = model.title
            binding.image.load(model.thumb)
        }
    }

    class MultipleViewHolder(val binding: ItemNestedBinding) : MyViewHolder(binding.root) {
        val adapter: SimpleAdapter<Model>
        var isPlaying = false

        init {
            adapter =
                SimpleAdapter.with<Model, NestedItemPlayerBinding>(R.layout.nested_item_player) { adapterPosition, model, binding ->
                    binding.frame.url = model.sources
                    binding.frame.imageView = binding.image
                    binding.image.load(model.thumb)


                    binding.frame.setOnClickListener {
                        //play and Pause
//                        binding.frame.playerView?.player?.playWhenReady = isPlaying

                        // Mute and UnMute
                       // binding.frame.isMute = !binding.frame.isMute

//                        if (isPlaying){
//                            binding.ivVolume.visibility==View.VISIBLE
//                        }else{
//                            binding.ivVolume.visibility==View.INVISIBLE
//                        }

//                        isPlaying = !isPlaying
                       /* binding.ivVolume.setImageResource(R.drawable.ic_volume_off).run {
                            apply { binding.ivVolume.visibility=View.VISIBLE }
                        }

                        if (binding.frame.isMute) {
                            binding.ivVolume.setImageResource(R.drawable.ic_volume_off).run {
                                apply { binding.ivVolume.visibility=View.VISIBLE }
                            }
                        } else {
                            binding.ivVolume.setImageResource(R.drawable.ic_volume_on)
                        }*/

                        val handler = Handler()
                        handler.postDelayed({
                            binding.ivVolume.visibility=View.INVISIBLE
                                            }, 1000)

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


                        //Listen for buffering listener
                        override fun onBuffering(isBuffering: Boolean) {
                            super.onBuffering(isBuffering)

                            binding.ivVolume.visibility = View.INVISIBLE
                            Log.e("TAG  buffer", isBuffering.toString())
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
                            binding.ivVolume.visibility = View.VISIBLE
                        }

                    }



                }
            val pagerSnapHelper = PagerSnapHelper()
            pagerSnapHelper.attachToRecyclerView(binding.recyclerView)
            binding.recyclerView.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.HORIZONTAL, false);
            binding.recyclerView.adapter = adapter


        }

        override fun onBind(model: Model) {

            binding.text.text = model.title
            adapter.clear()
            adapter.addAll(model.sourcesList.toList())
            adapter.notifyDataSetChanged()

        }


    }
}


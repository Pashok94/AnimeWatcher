package kpd.animewatcher.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import kpd.animewatcher.com.databinding.MainFragmentBinding
import kpd.animewatcher.utils.EndlessRecyclerViewScrollListener
import kpd.animewatcher.utils.FullScreenHelper
import kpd.animewatcher.viewModel.viewModels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent


class MainFragment : Fragment(), KoinComponent {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private lateinit var adapter: MainAdapter
    private var vb: MainFragmentBinding? = null
    private val fullScreenHelper: FullScreenHelper by lazy {
        FullScreenHelper(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = MainFragmentBinding.inflate(inflater, container, false)
        return vb?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
        initPlayer()
        observeViewModels()

    }

    private fun initPlayer() {
        vb?.let {
            with(it) {
                youtubePlayerView.enableAutomaticInitialization = false
                lifecycle.addObserver(youtubePlayerView)
                playerContainer.setOnClickListener {
                    youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                        override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.pause()
                        }
                    })
                    playerContainer.isVisible = false
                }
                val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        val defaultPlayerUiController =
                            DefaultPlayerUiController(youtubePlayerView, youTubePlayer)
                        youtubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                    }
                }

                youtubePlayerView.addFullScreenListener(object : YouTubePlayerFullScreenListener{
                    override fun onYouTubePlayerEnterFullScreen() {
                        fullScreenHelper.enterFullScreen()
                    }

                    override fun onYouTubePlayerExitFullScreen() {
                        fullScreenHelper.exitFullScreen()
                    }

                })

                val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
                youtubePlayerView.initialize(listener, options)
            }
        }
    }

    private fun initRV() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        adapter = MainAdapter(startVideoCallback)
        vb?.mainRv?.layoutManager = linearLayoutManager
        vb?.mainRv?.adapter = adapter
        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                mainViewModel.loadNewPageAnime(page + 1)
            }
        }
        vb?.mainRv?.addOnScrollListener(scrollListener)
    }

    private val startVideoCallback: (String) -> Unit = { trailerId ->
        vb?.playerContainer?.isVisible = true
        vb?.youtubePlayerView?.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(trailerId, 0f)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        vb?.youtubePlayerView?.release()
        vb = null
    }

    private fun observeViewModels() {
        mainViewModel.observeAnimeLiveData().observe(viewLifecycleOwner) {
            adapter.addNewAnime(it)
        }
    }

    companion object {
        const val JWT =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjE3NzciLCJuYmYiOjE2NTE5NTY2OTksImV4cCI6MTY1NDU0ODY5OSwiaWF0IjoxNjUxOTU2Njk5fQ.w5zgkQlkMJfefnkI_Lbs4YG98tzHTHeUokGXSXvFHB8"
    }
}
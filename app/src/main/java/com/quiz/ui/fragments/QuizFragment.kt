package com.quizApp.ui.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quizApp.R
import com.quizApp.data.level.SportChecker
import com.quizApp.databinding.FragmentQuizBinding
import com.quizApp.ui.adapters.AdapterAnimator
import com.quizApp.ui.adapters.levelAdapter.LevelAdapter
import com.quizApp.ui.adapters.levelAdapter.LevelClickListener
import com.quizApp.viewModels.LevelsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {
    var imageNumber = 0
    var direction = ""
    private val levelViewModel: LevelsViewModel by viewModels()
    private lateinit var binding: FragmentQuizBinding
    private lateinit var adapter: LevelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val prefOnBoard = requireActivity().getSharedPreferences("ON_BOARD" , Context.MODE_PRIVATE)
        val onBoard = prefOnBoard.getBoolean("ON_BOARD" , true)
        binding = FragmentQuizBinding.inflate(layoutInflater, container, false)
        adapter = LevelAdapter(object : LevelClickListener {
            override fun click(levelId: Int, countCompleted: Int) {
                findNavController().navigate(
                    R.id.questionsFragment,
                    Bundle().apply {
                        putInt("ID", levelId)
                        putInt("COUNT", countCompleted)
                    })

            }
        })


        with(binding) {

            if (onBoard){
                cardOnBoard.visibility = View.VISIBLE
                prefOnBoard.edit()
                    .putBoolean("ON_BOARD" , false)
                    .apply()
            }

            buttonSkip.setOnClickListener {
                cardOnBoard.visibility = View.GONE
            }


            recycler.adapter = adapter
            buttonRight.setOnClickListener {
                when (imageNumber) {
                    0 -> {
                        direction = "RIGHT"
                        levelViewModel.getBasketballLevels()
                        imageNumber++

                    }

                    1 -> {
                        direction = "RIGHT"
                        levelViewModel.getHockeyLevels()
                        imageNumber++


                    }

                    2 -> {
                        direction = "RIGHT"
                        levelViewModel.getUsaFootballLevels()
                        imageNumber++

                    }

                    3 -> {
                        direction = "RIGHT"
                        levelViewModel.getFootballLevels()
                        imageNumber = 0


                    }
                }


            }

            buttonLeft.setOnClickListener {
                when (imageNumber) {
                    0 -> {
                        direction = "LEFT"
                        levelViewModel.getUsaFootballLevels()
                        imageNumber = 3

                    }

                    1 -> {
                        direction = "LEFT"
                        levelViewModel.getFootballLevels()
                        imageNumber--


                    }

                    2 -> {
                        direction = "LEFT"
                        levelViewModel.getBasketballLevels()
                        imageNumber--

                    }

                    3 -> {
                        direction = "LEFT"
                        levelViewModel.getHockeyLevels()
                        imageNumber--


                    }
                }

            }
        }

        levelViewModel.levelsLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it.list)
        }




        fun clickRight(imageId: Int) {
            with(binding) {
                val disappearAnimator = ObjectAnimator.ofFloat(
                    imageSlide,
                    "translationX",
                    0f,
                    -imageSlide.width.toFloat()
                )
                disappearAnimator.duration = 300

                // Анимация появления ImageView
                val appearAnimator = ObjectAnimator.ofFloat(
                    imageSlide,
                    "translationX",
                    imageSlide.width.toFloat(),
                    0f
                )
                appearAnimator.duration = 450

                // Изменение изображения в конце анимации исчезновения
                disappearAnimator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        imageSlide.setImageResource(imageId)
                        super.onAnimationEnd(animation)
                    }
                })

                // Запуск анимаций последовательно
                val animatorSet = AnimatorSet()
                animatorSet.playSequentially(disappearAnimator, appearAnimator)
                animatorSet.start()

            }
        }

        fun clickLeft(imageId: Int) {
            with(binding) {
                val disappearAnimator = ObjectAnimator.ofFloat(
                    imageSlide,
                    "translationX",
                    0f,
                    imageSlide.width.toFloat()
                )
                disappearAnimator.duration = 300

                // Анимация появления ImageView
                val appearAnimator = ObjectAnimator.ofFloat(
                    imageSlide,
                    "translationX",
                    -imageSlide.width.toFloat(),
                    0f
                )
                appearAnimator.duration = 450

                // Изменение изображения в конце анимации исчезновения
                disappearAnimator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        imageSlide.setImageResource(imageId)
                        imageSlide.tag = "football"
                        super.onAnimationEnd(animation)
                    }
                })

                // Запуск анимаций последовательно
                val animatorSet = AnimatorSet()
                animatorSet.playSequentially(disappearAnimator, appearAnimator)
                animatorSet.start()


            }
        }

        levelViewModel.sportCheckerLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is SportChecker.Football -> {
                    if (direction == "RIGHT") {
                        clickRight(R.drawable.img_football)
                    } else {
                        clickLeft(R.drawable.img_football)
                    }
                }

                is SportChecker.BasketBall -> {
                    if (direction == "RIGHT") {
                        clickRight(R.drawable.image_basket)
                    } else {
                        clickLeft(R.drawable.image_basket)

                    }
                }

                is SportChecker.Hockey -> {
                    if (direction == "RIGHT") {
                        clickRight(R.drawable.image_hockey)
                    } else {
                        clickLeft(R.drawable.image_hockey)

                    }
                }

                is SportChecker.UsaFootball -> {
                    if (direction == "RIGHT") {
                        clickRight(R.drawable.img_american_fottbnall)
                    } else {
                        clickLeft(R.drawable.img_american_fottbnall)

                    }
                }

                else -> {}
            }

        }

        levelViewModel.getFootballLevels()
        return binding.root
    }


}
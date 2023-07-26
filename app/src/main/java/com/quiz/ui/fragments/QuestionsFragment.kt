package com.quizApp.ui.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quizApp.data.level.QuestionModel
import com.quizApp.databinding.FragmentQuestionsBinding
import com.quizApp.viewModels.LevelsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class QuestionsFragment : Fragment() {
    private lateinit var binding: FragmentQuestionsBinding
    private val levelsViewModel: LevelsViewModel by viewModels()
    private lateinit var useQuestionsList: List<QuestionModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuestionsBinding.inflate(layoutInflater, container, false)
        val pres = requireActivity().getSharedPreferences("ENERGY", Context.MODE_PRIVATE)
        var isWorkStarted = pres.getBoolean("WORK" , false)

        var energy = pres.getInt("ENERGY", 3)

        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                isWorkStarted = true
                pres.edit()
                    .putBoolean("WORK" , isWorkStarted)
                    .apply()
                val energyPref = pres.getInt("ENERGY", 3)
                if (energyPref < 3) {
                    pres.edit()
                        .putInt("ENERGY", energyPref + 1)
                        .apply()

                } else if (energy == 3) {
                    cancel()
                    isWorkStarted = false
                    pres.edit()
                        .putBoolean("WORK" , isWorkStarted)
                        .apply()
                }
            }
        }

        val levelId = requireArguments().getInt("ID")
        var count = requireArguments().getInt("COUNT")
        levelsViewModel.getQuestionsByLevelId(levelId)

        with(binding) {
            textAnswer1.setOnClickListener {
                if (pres.getInt("ENERGY", 3) > 0) {
                    for (i in useQuestionsList[count].answers.withIndex()) {
                        if (i.value.isTrue) {
                            when (i.index) {
                                0 -> {
                                    card1.setCardBackgroundColor(Color.GREEN)
                                    levelsViewModel.answerTrue(levelId)
                                    buttonNext.visibility = View.VISIBLE
                                }

                                1 -> {
                                    card2.setCardBackgroundColor(Color.GREEN)
                                    buttonReply.visibility = View.VISIBLE
                                    levelsViewModel.useEnergy()
                                    if (!isWorkStarted) {
                                        isWorkStarted = true
                                        timer.schedule(task, 3_600_000, 3_600_000)
                                    }
                                    pres.edit()
                                        .putInt("ENERGY", pres.getInt("ENERGY", 3)-1)
                                        .apply()

                                }

                                2 -> {
                                    card3.setCardBackgroundColor(Color.GREEN)
                                    buttonReply.visibility = View.VISIBLE
                                    levelsViewModel.useEnergy()

                                    if (!isWorkStarted) {
                                        isWorkStarted = true
                                        timer.schedule(task, 3_600_000, 3_600_000)
                                    }
                                    pres.edit()
                                        .putInt("ENERGY", pres.getInt("ENERGY", 3)-1)
                                        .apply()
                                }

                                else -> {
                                    card4.setCardBackgroundColor(Color.GREEN)
                                    buttonReply.visibility = View.VISIBLE
                                    levelsViewModel.useEnergy()
                                    if (!isWorkStarted) {
                                        isWorkStarted = true
                                        timer.schedule(task, 3_600_000, 3_600_000)
                                    }
                                    pres.edit()
                                        .putInt("ENERGY", pres.getInt("ENERGY", 3)-1)
                                        .apply()
                                }


                            }
                        }
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Energy is over. Come back in one hour.",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
            textAnswer2.setOnClickListener {
                if (pres.getInt("ENERGY", 3) > 0) {

                    for (i in useQuestionsList[count].answers.withIndex()) {
                        if (i.value.isTrue) {
                            when (i.index) {
                                0 -> {
                                    card1.setCardBackgroundColor(Color.GREEN)
                                    buttonReply.visibility = View.VISIBLE
                                    levelsViewModel.useEnergy()
                                    if (!isWorkStarted) {
                                        isWorkStarted = true
                                        timer.schedule(task, 3_600_000, 3_600_000)
                                    }
                                    pres.edit()
                                        .putInt("ENERGY", pres.getInt("ENERGY", 3) -1)
                                        .apply()
                                }

                                1 -> {
                                    card2.setCardBackgroundColor(Color.GREEN)
                                    levelsViewModel.answerTrue(levelId)
                                    binding.buttonNext.visibility = View.VISIBLE

                                }

                                2 -> {
                                    card3.setCardBackgroundColor(Color.GREEN)
                                    buttonReply.visibility = View.VISIBLE
                                    levelsViewModel.useEnergy()
                                    if (!isWorkStarted) {
                                        isWorkStarted = true
                                        timer.schedule(task, 3_600_000, 3_600_000)
                                    }
                                    pres.edit()
                                        .putInt("ENERGY", pres.getInt("ENERGY", 3) -1)
                                        .apply()
                                }

                                else -> {
                                    card4.setCardBackgroundColor(Color.GREEN)
                                    buttonReply.visibility = View.VISIBLE
                                    levelsViewModel.useEnergy()
                                    if (!isWorkStarted) {
                                        isWorkStarted = true
                                        timer.schedule(task, 3_600_000, 3_600_000)
                                    }
                                    pres.edit()
                                        .putInt("ENERGY", pres.getInt("ENERGY", 3) -1)
                                        .apply()
                                }


                            }
                        }
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Energy is over. Come back in one hour.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            textAnswer3.setOnClickListener {
                if (pres.getInt("ENERGY", 3) > 0) {



                    for (i in useQuestionsList[count].answers.withIndex()) {
                        if (i.value.isTrue) {
                            when (i.index) {
                                0 -> {
                                    card1.setCardBackgroundColor(Color.GREEN)
                                    buttonReply.visibility = View.VISIBLE
                                    levelsViewModel.useEnergy()
                                    if (!isWorkStarted) {
                                        isWorkStarted = true
                                        timer.schedule(task, 3_600_000, 3_600_000)
                                    }
                                    pres.edit()
                                        .putInt("ENERGY", pres.getInt("ENERGY", 3) -1)
                                        .apply()
                                }

                                1 -> {
                                    card2.setCardBackgroundColor(Color.GREEN)
                                    buttonReply.visibility = View.VISIBLE
                                    levelsViewModel.useEnergy()
                                    if (!isWorkStarted) {
                                        isWorkStarted = true
                                        timer.schedule(task, 3_600_000, 3_600_000)
                                    }
                                    pres.edit()
                                        .putInt("ENERGY", pres.getInt("ENERGY", 3) -1)
                                        .apply()
                                }

                                2 -> {
                                    card3.setCardBackgroundColor(Color.GREEN)
                                    levelsViewModel.answerTrue(levelId)
                                    binding.buttonNext.visibility = View.VISIBLE

                                }

                                else -> {
                                    card4.setCardBackgroundColor(Color.GREEN)
                                    buttonReply.visibility = View.VISIBLE
                                    levelsViewModel.useEnergy()
                                    if (!isWorkStarted) {
                                        isWorkStarted = true
                                        timer.schedule(task, 3_600_000, 3_600_000)
                                    }
                                    pres.edit()
                                        .putInt("ENERGY", pres.getInt("ENERGY", 3) -1)
                                        .apply()
                                }


                            }
                        }
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Energy is over. Come back in one hour.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            textAnswer4.setOnClickListener {
                if (pres.getInt("ENERGY", 3) > 0) {


                    for (i in useQuestionsList[count].answers.withIndex()) {
                        if (i.value.isTrue) {
                            when (i.index) {
                                0 -> {
                                    card1.setCardBackgroundColor(Color.GREEN)
                                    buttonReply.visibility = View.VISIBLE
                                    levelsViewModel.useEnergy()
                                    if (!isWorkStarted) {
                                        isWorkStarted = true
                                        timer.schedule(task, 3_600_000, 3_600_000)
                                    }
                                    pres.edit()
                                        .putInt("ENERGY", pres.getInt("ENERGY", 3)-1)
                                        .apply()
                                }

                                1 -> {
                                    card2.setCardBackgroundColor(Color.GREEN)
                                    buttonReply.visibility = View.VISIBLE
                                    levelsViewModel.useEnergy()
                                    if (!isWorkStarted) {
                                        isWorkStarted = true
                                        timer.schedule(task, 3_600_000, 3_600_000)
                                    }
                                    pres.edit()
                                        .putInt("ENERGY", pres.getInt("ENERGY", 3)-1)
                                        .apply()
                                }

                                2 -> {
                                    card3.setCardBackgroundColor(Color.GREEN)
                                    buttonReply.visibility = View.VISIBLE
                                    levelsViewModel.useEnergy()
                                    if (!isWorkStarted) {
                                        isWorkStarted = true
                                        timer.schedule(task, 3_600_000, 3_600_000)
                                    }
                                    pres.edit()
                                        .putInt("ENERGY", pres.getInt("ENERGY", 3)-1)
                                        .apply()
                                }

                                else -> {
                                    card4.setCardBackgroundColor(Color.GREEN)
                                    levelsViewModel.answerTrue(levelId)
                                    binding.buttonNext.visibility = View.VISIBLE

                                }


                            }
                        }
                    }

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Energy is over. Come back in one hour.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            buttonReply.setOnClickListener {
                buttonReply.visibility = View.GONE
                card1.setCardBackgroundColor(Color.WHITE)
                card2.setCardBackgroundColor(Color.WHITE)
                card3.setCardBackgroundColor(Color.WHITE)
                card4.setCardBackgroundColor(Color.WHITE)

            }

            buttonNext.setOnClickListener {
                buttonNext.visibility = View.GONE
                count++
                if (count < 10) {
                    levelsViewModel.getQuestionsByLevelId(levelId)
                } else if (count == 10) {
                    Toast.makeText(requireContext(), "Complete", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
                card1.setCardBackgroundColor(Color.WHITE)
                card2.setCardBackgroundColor(Color.WHITE)
                card3.setCardBackgroundColor(Color.WHITE)
                card4.setCardBackgroundColor(Color.WHITE)
            }

            buttonClose.setOnClickListener {
                findNavController().navigateUp()
            }
        }



        levelsViewModel.questionsLiveData.observe(viewLifecycleOwner) {
            try {
                if (it.isNotEmpty()) {
                    useQuestionsList = it
                    with(binding) {
                        textTitle.text = it[count].title
                        textAnswer1.text = it[count].answers[0].answer
                        textAnswer2.text = it[count].answers[1].answer
                        textAnswer3.text = it[count].answers[2].answer
                        textAnswer4.text = it[count].answers[3].answer
                    }
                }

            } catch (e: Exception) {
                Log.d("tag", "$e")
            }

        }

        levelsViewModel.energyLiveData.observe(viewLifecycleOwner) {
            binding.textCountEnergy.text = it.toString()
        }

        return binding.root
    }

}
package com.example.newsapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {
    private lateinit var viewBinding: FragmentCategoriesBinding
    lateinit var categoryClickListener: CategoryClickListener
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.ivSports.setOnClickListener {
            categoryClickListener.onCategoryClicked("sports")
        }
        viewBinding.ivPolitics.setOnClickListener {
            categoryClickListener.onCategoryClicked("general")
        }
        viewBinding.ivHealth.setOnClickListener {
            categoryClickListener.onCategoryClicked("health")
        }
        viewBinding.ivBusiness.setOnClickListener {
            categoryClickListener.onCategoryClicked("business")
        }
        viewBinding.ivEnvironment.setOnClickListener {
            categoryClickListener.onCategoryClicked("general")
        }
        viewBinding.ivScience.setOnClickListener {
            categoryClickListener.onCategoryClicked("science")
        }

    }

    interface CategoryClickListener {
        fun onCategoryClicked(category: String)
    }

}
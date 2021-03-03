package fr.juliettebois.dmii.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import fr.juliettebois.dmii.myapplication.adapters.CategoryAdapter
import fr.juliettebois.dmii.myapplication.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(homeViewModel) {
            token.observe(
                viewLifecycleOwner,
                Observer {
                    // récupérer les catégories
                    getCategories()
                }
            )

            categories.observe(
                viewLifecycleOwner,
                Observer {
                    binding.categoryList.layoutManager = LinearLayoutManager(context)
                    binding.categoryList.adapter = CategoryAdapter(it)
                }
            )

            error.observe(
                viewLifecycleOwner,
                Observer {
                    // afficher l'erreur
                }
            )
        }
    }
}

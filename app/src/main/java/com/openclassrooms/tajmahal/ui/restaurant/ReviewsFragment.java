package com.openclassrooms.tajmahal.ui.restaurant;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.tajmahal.databinding.FragmentReviewsBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class ReviewsFragment extends Fragment {

    private FragmentReviewsBinding binding;

    public static ReviewsFragment newInstance() {
        return new ReviewsFragment();
    }

    private ReviewsRecyclerViewAdapter adapter;

    private DetailsViewModel detailsViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * The fragment should not add the view itself but return it.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return Returns the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReviewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Initializes the ViewModel for this activity.
     */
    private void setupViewModel() {
        detailsViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
    }

    /**
     * This method is called immediately after `onCreateView()`.
     * Use this method to perform final initialization once the fragment views have been inflated.
     *
     * @param view The View returned by `onCreateView()`.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        String value = "";
        if (getArguments() != null) {
            value = getArguments().getString("RestaurantName");
        }
        binding.tvRestaurantName.setText(value);

        binding.iconBack.setOnClickListener(v-> requireActivity().onBackPressed());
        setupRecyclerView();
        detailsViewModel.getReviews().observe(getViewLifecycleOwner(), deque -> {
            // Convertir Deque en List
            List<Review> reviewList = new ArrayList<>(deque);
            updateReviewList(reviewList);
        });
    }

    /**
     * Configures the RecyclerView to display the list of reviews.
     */
    private void setupRecyclerView() {
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new ReviewsRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
    }

    /**
     * Updates the list of reviews displayed in the adapter.
     * @param reviews The updated review list.
     */
    private void updateReviewList(List<Review> reviews) {
        Log.d("Lucas", reviews.toString());
        adapter.submitList(reviews);
    }

}
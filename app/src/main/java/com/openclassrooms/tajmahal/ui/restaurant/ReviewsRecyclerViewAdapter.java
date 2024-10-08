package com.openclassrooms.tajmahal.ui.restaurant;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.tajmahal.databinding.ReviewItemBinding;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.squareup.picasso.Picasso;

public class ReviewsRecyclerViewAdapter extends ListAdapter<Review, ReviewsRecyclerViewAdapter.ViewHolder> {
    protected ReviewsRecyclerViewAdapter() {
        super(new ItemCallback());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ReviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ReviewItemBinding binding;

        /**
         * Constructeur du ViewHolder.
         */
        public ViewHolder(@NonNull ReviewItemBinding b) {
            super(b.getRoot());
            binding = b;
        }

        /**
         * Lie les données de la tâche au ViewHolder.
         * Description(textview) et date d'échéance(textview et progress indicator)
         *
         * @param review La tâche à afficher.
         */
        public void bind(Review review) {
            Picasso.get().load(review.getPicture()).into(binding.userImage);
            binding.experienceEditText.setText(review.getComment());
            binding.userName.setText(review.getUsername());
            binding.ratingBar.setRating(review.getRate());
        }

    }
    /**
     * Callback pour la comparaison des éléments de la liste.
     */
    private static class ItemCallback extends DiffUtil.ItemCallback<Review> {
        @Override
        public boolean areItemsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            //pas de modification des reviews donc pas besoin de modifier
            return oldItem.equals(newItem);
        }
    }

}
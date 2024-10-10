package com.openclassrooms.tajmahal.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.tajmahal.data.exceptions.NoMessageException;
import com.openclassrooms.tajmahal.data.exceptions.NoRatingException;
import com.openclassrooms.tajmahal.data.service.RestaurantApi;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.Deque;

import javax.inject.Inject;

public class ReviewsRepository {

    // The API interface instance that will be used for network requests related to restaurant data.
    private final RestaurantApi restaurantApi;

    /**
     * Constructs a new instance of {@link RestaurantRepository} with the given {@link RestaurantApi}.
     *
     * @param restaurantApi The network API interface for fetching restaurant data.
     */
    @Inject
    public ReviewsRepository(RestaurantApi restaurantApi) {
        this.restaurantApi = restaurantApi;
    }

    /**
     * Fetches the reviews
     * <p>
     * This method will make a network call using the provided {@link RestaurantApi} instance
     * to fetch restaurant reviews. Note that error handling and any transformations on the data
     * would need to be managed.
     *
     * @return LiveData list holding the reviews details.
     */
    public LiveData<Deque<Review>> getReviews() {
        return new MutableLiveData<>(restaurantApi.getReviews());
    }

    /**
     * Add a Review
     *
     * @param userName The name of the user submitting the review.
     * @param picture  The URL of the picture associated with the review.
     * @param comment  The user's comment or feedback.
     * @param rate     The rating given by the user, typically between 1 and 5.
     * @throws NoMessageException if there is no message in the Review
     * @throws NoRatingException  if there is no rating in the Review
     */
    public void addReview(String userName, String picture, String comment, int rate)
            throws NoRatingException, NoMessageException {
        if (rate == 0) {
            throw new NoRatingException();
        }
        if (comment.isEmpty()) {
            throw new NoMessageException();
        }
        Review review = new Review(userName, picture, comment, rate);
        restaurantApi.addReview(review);
    }

}

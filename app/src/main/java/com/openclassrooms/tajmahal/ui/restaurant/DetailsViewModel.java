package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.exceptions.NoMessageException;
import com.openclassrooms.tajmahal.data.exceptions.NoRatingException;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.repository.ReviewsRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.Calendar;
import java.util.Deque;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * MainViewModel is responsible for preparing and managing the data for the {@link DetailsFragment}.
 * It communicates with the {@link RestaurantRepository} to fetch restaurant details and provides
 * utility methods related to the restaurant UI.
 * <p>
 * This ViewModel is integrated with Hilt for dependency injection.
 */
@HiltViewModel
public class DetailsViewModel extends ViewModel {

    private final RestaurantRepository restaurantRepository;
    private final ReviewsRepository reviewsRepository;

    LiveData<Deque<Review>> reviews;

    /**
     * Constructor that Hilt will use to create an instance of MainViewModel.
     *
     * @param reviewsRepository    The repository which will provide reviews data.
     * @param restaurantRepository The repository which will provide restaurant data.
     */
    @Inject
    public DetailsViewModel(RestaurantRepository restaurantRepository, ReviewsRepository reviewsRepository) {
        this.restaurantRepository = restaurantRepository;
        this.reviewsRepository = reviewsRepository;
    }

    /**
     * Fetches the details of the Taj Mahal restaurant.
     *
     * @return LiveData object containing the details of the Taj Mahal restaurant.
     */
    public LiveData<Restaurant> getTajMahalRestaurant() {
        return restaurantRepository.getRestaurant();
    }

    /**
     * Initializes the `reviews` LiveData by fetching reviews from the restaurant repository.
     */
    private void setUpReviews() {
        reviews = reviewsRepository.getReviews();
    }

    /**
     * Returns the LiveData object that contains the list of reviews.
     * If the reviews have not been initialized, it calls `setUpReviews()` to fetch them.
     *
     * @return LiveData containing a list of Review objects, or null if not yet initialized.
     */
    public LiveData<Deque<Review>> getReviews() {
        if (reviews == null) {
            setUpReviews();
        }
        return reviews;
    }

    /**
     * Returns the number of reviews available.
     * If there are no reviews or the reviews have not been fetched yet, it returns 0.
     *
     * @return the number of reviews, or 0 if none are available.
     */
    public int numberOfReview() {
        if (reviewIsEmpty()) {
            return 0;
        }
        return Objects.requireNonNull(reviews.getValue()).size();
    }

    /**
     * Return the number of gived notation divided by number of notation.
     *
     * @return the average of that element
     */
    public double notationAverage(int notation) {
        if (reviewIsEmpty()) {
            return 0;
        }
        int numberOfNotation = 0;
        for (Review reviews : Objects.requireNonNull(reviews.getValue())) {
            if (reviews.getRate() == notation) {
                numberOfNotation += 1;
            }
        }
        return (double) numberOfNotation / numberOfReview();
    }

    /**
     * Checks if the reviews are either null or empty.
     * If the reviews are not initialized, it calls `setUpReviews()` to fetch them.
     *
     * @return true if the reviews are null or empty, false otherwise.
     */
    private Boolean reviewIsEmpty() {
        if (reviews == null) {
            setUpReviews();
        }
        return reviews.getValue() == null || reviews.getValue().isEmpty();
    }

    /**
     * Calculates and returns the average rating of all reviews.
     * If there are no reviews, it returns 0.
     *
     * @return the average rating as a double, or 0 if there are no reviews.
     */
    public double averageReview() {
        if (reviewIsEmpty()) {
            return 0;
        }

        Deque<Review> reviewList = reviews.getValue();

        //For IDE null already tested in reviewIsEmpty
        assert reviewList != null;

        //Average of review
        return reviewList.stream()
                .mapToInt(Review::getRate)
                .average()
                .orElse(0);
    }

    /**
     * Add a Review on the top of the list
     *
     * @param userName The name of the user submitting the review.
     * @param picture  The URL of the picture associated with the review.
     * @param comment  The user's comment or feedback.
     * @param rate     The rating given by the user, typically between 1 and 5.
     * @return ReviewAddValidation the error state  (complete when all is good)
     */
    public ReviewAddValidation addReview(String userName, String picture, String comment, int rate) {
        try {
            reviewsRepository.addReview(userName, picture, comment, rate);
        } catch (NoRatingException e) {
            return ReviewAddValidation.NORATING;
        } catch (NoMessageException e) {
            return ReviewAddValidation.NOMESSAGE;
        }
        return ReviewAddValidation.COMPLETE;
    }


    /**
     * Retrieves the current day of the week in French.
     *
     * @return A string representing the current day of the week in French.
     */
    public String getCurrentDay(Context context) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String dayString;

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                dayString = context.getString(R.string.monday);
                break;
            case Calendar.TUESDAY:
                dayString = context.getString(R.string.tuesday);
                break;
            case Calendar.WEDNESDAY:
                dayString = context.getString(R.string.wednesday);
                break;
            case Calendar.THURSDAY:
                dayString = context.getString(R.string.thursday);
                break;
            case Calendar.FRIDAY:
                dayString = context.getString(R.string.friday);
                break;
            case Calendar.SATURDAY:
                dayString = context.getString(R.string.saturday);
                break;
            case Calendar.SUNDAY:
                dayString = context.getString(R.string.sunday);
                break;
            default:
                dayString = "";
        }
        return dayString;
    }

}

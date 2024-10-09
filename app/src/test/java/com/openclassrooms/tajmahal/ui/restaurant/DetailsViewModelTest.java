package com.openclassrooms.tajmahal.ui.restaurant;

import static org.junit.jupiter.api.Assertions.*;

import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.repository.ReviewsRepository;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.domain.model.Review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Deque;

@ExtendWith(MockitoExtension.class)
class DetailsViewModelTest {

    @Mock
    DetailsViewModel detailsViewModel;

    @Mock
    RestaurantFakeApi restaurantApi;


    @BeforeEach
    void SetUp(){
        restaurantApi = new RestaurantFakeApi();
        detailsViewModel = new DetailsViewModel(new RestaurantRepository(restaurantApi), new ReviewsRepository(restaurantApi));
    }

    @Test
    void Add_Review() {
        //Arrange
        Review validReview = new Review("Excellent product", "FakeURL", "Super !", 5);

        //Act
        ReviewAddValidation result = detailsViewModel.addReview(validReview);

        //Assert
        assertEquals(ReviewAddValidation.COMPLETE, result);
    }

    @Test
    void Add_Review_No_Message() {
        //Arrange
        Review validReview = new Review("Excellent product", "FakeURL", "", 5);

        //Act
        ReviewAddValidation result = detailsViewModel.addReview(validReview);

        //Assert
        assertEquals(ReviewAddValidation.NOMESSAGE, result);
    }

    @Test
    void Add_Review_No_Rating() {
        //Arrange
        Review validReview = new Review("Excellent product", "FakeURL", "Super !", 0);

        //Act
        ReviewAddValidation result = detailsViewModel.addReview(validReview);

        //Assert
        assertEquals(ReviewAddValidation.NORATING, result);
    }

    @Test
    void Add_Review_Valid_Review_Returns_Complete() {
        // Arrange
        Review validReview = new Review("Excellent product", "FakeURL", "Super !", 5);
        restaurantApi.resetReview();

        // Act
        ReviewAddValidation result = detailsViewModel.addReview(validReview);
        Deque<Review> reviews = detailsViewModel.getReviews().getValue();


        // Assert
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals(validReview, reviews.getFirst());
    }

    @Test
    void Add_Review_No_Message_Returns_No_Message() {
        // Arrange
        Review reviewWithoutMessage = new Review("Excellent product", "FakeURL", "", 5);
        restaurantApi.resetReview();

        // Act
        ReviewAddValidation result = detailsViewModel.addReview(reviewWithoutMessage);
        Deque<Review> reviews = detailsViewModel.getReviews().getValue();


        // Assert
        assertNotNull(reviews);
        assertEquals(0, reviews.size(), "Review with no message should not be added");
    }

    @Test
    void Add_Review_NoRating_Returns_No_Rating() {
        // Arrange
        Review reviewWithoutRating = new Review("Excellent product", "FakeURL", "Super !", 0);
        restaurantApi.resetReview();

        // Act
        ReviewAddValidation result = detailsViewModel.addReview(reviewWithoutRating);
        Deque<Review> reviews = detailsViewModel.getReviews().getValue();


        // Assert
        assertNotNull(reviews);
        assertEquals(0, reviews.size(), "Review with no rating should not be added");
    }





}
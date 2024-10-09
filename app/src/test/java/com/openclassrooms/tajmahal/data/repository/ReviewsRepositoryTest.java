package com.openclassrooms.tajmahal.data.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.openclassrooms.tajmahal.data.exceptions.NoMessageException;
import com.openclassrooms.tajmahal.data.exceptions.NoRatingException;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.domain.model.Review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ReviewsRepositoryTest {

    @Mock
    private ReviewsRepository repository;

    @Mock
    RestaurantFakeApi restaurantApi;

    @BeforeEach
    void SetUp(){
        restaurantApi = new RestaurantFakeApi();
        repository = new ReviewsRepository(restaurantApi);
    }

    @Test
    void Complete_Add_Does_Not_Throw() {
        //Arrange
        Review review = new Review("Michel", "FakeUrl", "fdf", 5);


        // Act & Assert
        assertDoesNotThrow(() -> repository.addReview(review));

    }

    @Test
    void No_Message_Add_Throw() {
        //Arrange
        Review review = new Review("Michel", "FakeUrl", "", 5);


        // Act & Assert
        assertThrows(NoMessageException.class, () -> repository.addReview(review));
    }

    @Test
    void No_Review_Add_Throw() {
        //Arrange
        Review review = new Review("Michel", "FakeUrl", "fdf", 0);


        // Act & Assert
        assertThrows(NoRatingException.class, () -> repository.addReview(review));
    }

    @Test
    void Add_Review_Complete_Add_To_Review() throws NoRatingException, NoMessageException {
        //Arrange
        Review review = new Review("Michel", "FakeUrl", "fdf", 5);
        restaurantApi.resetReview();

        // Act
        repository.addReview(review);
        Deque<Review> value = repository.getReviews().getValue();

        //Assert
        if (value == null) {
            fail("Unable to get reviews");
        }
        assertEquals(1, value.size());
        assertEquals(review, value.getFirst());
    }

    @Test
    void Add_Review_Without_Message_Does_Not_Add_To_Reviews() {
        // Arrange
        Review reviewWithoutMessage = new Review("Michel", "FakeUrl", "", 5);  // Empty comment
        restaurantApi.resetReview();

        // Act
        try {
            repository.addReview(reviewWithoutMessage);  // Add review with no message
        } catch (NoRatingException | NoMessageException ignored) {

        }
        Deque<Review> value = repository.getReviews().getValue();

        // Assert
        if (value == null) {
            fail("Unable to get reviews");
        }
        assertEquals(0, value.size(), "Review without a message should not be added");
    }

    @Test
    void Add_Review_Without_Rating_Does_Not_Add_To_Reviews() {
        // Arrange
        Review reviewWithoutMessage = new Review("Michel", "FakeUrl", "Nul !", 0);  // Empty comment
        restaurantApi.resetReview();

        // Act
        try {
            repository.addReview(reviewWithoutMessage);  // Add review with no message
        } catch (NoRatingException | NoMessageException ignored) {

        }
        Deque<Review> value = repository.getReviews().getValue();

        // Assert
        if (value == null) {
            fail("Unable to get reviews");
        }
        assertEquals(0, value.size(), "Review without a message should not be added");
    }


}
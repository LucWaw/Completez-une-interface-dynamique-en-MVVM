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


import java.util.Deque;


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
        String userName = "Excellent product";
        String picture = "FakeURL";
        String comment = "Super !";
        int rate = 5;

        // Act & Assert
        assertDoesNotThrow(() -> repository.addReview(userName, picture, comment, rate));

    }

    @Test
    void No_Message_Add_Throw() {
        //Arrange
        String userName = "Excellent product";
        String picture = "FakeURL";
        String comment = "";
        int rate = 5;

        // Act & Assert
        assertThrows(NoMessageException.class, () -> repository.addReview(userName, picture, comment, rate));
    }

    @Test
    void No_Review_Add_Throw() {
        //Arrange
        String userName = "Excellent product";
        String picture = "FakeURL";
        String comment = "Super !";
        int rate = 0;

        // Act & Assert
        assertThrows(NoRatingException.class, () -> repository.addReview(userName, picture, comment, rate));
    }

    @Test
    void Add_Review_Complete_Add_To_Review() throws NoRatingException, NoMessageException {
        //Arrange
        String userName = "Excellent product";
        String picture = "FakeURL";
        String comment = "Super !";
        int rate = 5;
        restaurantApi.resetReview();

        // Act
        repository.addReview(userName, picture, comment, rate);
        Deque<Review> reviews = repository.getReviews().getValue();

        //Assert
        if (reviews == null) {
            fail("Unable to get reviews");
        }
        assertEquals(1, reviews.size());
        assertEquals(userName, reviews.getFirst().getUsername());
        assertEquals(comment, reviews.getFirst().getComment());
        assertEquals(picture, reviews.getFirst().getPicture());
        assertEquals(rate, reviews.getFirst().getRate());
    }

    @Test
    void Add_Review_Without_Message_Does_Not_Add_To_Reviews() {
        // Arrange
        String userName = "Excellent product";
        String picture = "FakeURL";
        String comment = "Super !";
        int rate = 0;
        restaurantApi.resetReview();

        // Act
        try {
            repository.addReview(userName, picture, comment, rate);  // Add review with no message
        } catch (NoRatingException | NoMessageException ignored) {

        }
        Deque<Review> reviews = repository.getReviews().getValue();

        // Assert
        if (reviews == null) {
            fail("Unable to get reviews");
        }
        assertEquals(0, reviews.size(), "Review without a message should not be added");
    }

    @Test
    void Add_Review_Without_Rating_Does_Not_Add_To_Reviews() {
        // Arrange
        String userName = "Excellent product";
        String picture = "FakeURL";
        String comment = "Super !";
        int rate = 0;
        restaurantApi.resetReview();

        // Act
        try {
            repository.addReview(userName, picture, comment, rate);  // Add review with no message
        } catch (NoRatingException | NoMessageException ignored) {

        }
        Deque<Review> reviews = repository.getReviews().getValue();

        // Assert
        if (reviews == null) {
            fail("Unable to get reviews");
        }
        assertEquals(0, reviews.size(), "Review without a message should not be added");
    }


}
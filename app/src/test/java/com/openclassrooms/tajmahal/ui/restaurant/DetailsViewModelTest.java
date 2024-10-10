package com.openclassrooms.tajmahal.ui.restaurant;

import static org.junit.jupiter.api.Assertions.*;


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
        // Arrange
        String userName = "Excellent product";
        String picture = "FakeURL";
        String comment = "Super !";
        int rate = 5;

        // Act
        ReviewAddValidation result = detailsViewModel.addReview(userName, picture, comment, rate);


        //Assert
        assertEquals(ReviewAddValidation.COMPLETE, result);
    }

    @Test
    void Add_Review_No_Message() {
        //Arrange
        String userName = "Excellent product";
        String picture = "FakeURL";
        String comment = "";
        int rate = 5;

        //Act
        ReviewAddValidation result = detailsViewModel.addReview(userName, picture, comment, rate);

        //Assert
        assertEquals(ReviewAddValidation.NOMESSAGE, result);
    }

    @Test
    void Add_Review_No_Rating() {
        //Arrange
        String userName = "Excellent product";
        String picture = "FakeURL";
        String comment = "Super !";
        int rate = 0;

        //Act
        ReviewAddValidation result = detailsViewModel.addReview(userName, picture, comment, rate);

        //Assert
        assertEquals(ReviewAddValidation.NORATING, result);
    }

    @Test
    void Add_Review_Valid_Review_Returns_Complete() {
        // Arrange
        String userName = "Excellent product";
        String picture = "FakeURL";
        String comment = "Super !";
        int rate = 5;
        restaurantApi.resetReview();

        // Act
        detailsViewModel.addReview(userName, picture, comment, rate);
        Deque<Review> reviews = detailsViewModel.getReviews().getValue();


        // Assert
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals(userName, reviews.getFirst().getUsername());
        assertEquals(comment, reviews.getFirst().getComment());
        assertEquals(picture, reviews.getFirst().getPicture());
        assertEquals(rate, reviews.getFirst().getRate());

    }

    @Test
    void Add_Review_No_Message_Returns_No_Message() {
        // Arrange
        String userName = "Excellent product";
        String picture = "FakeURL";
        String comment = "";
        int rate = 5;
        restaurantApi.resetReview();

        // Act
        detailsViewModel.addReview(userName, picture, comment, rate);
        Deque<Review> reviews = detailsViewModel.getReviews().getValue();


        // Assert
        assertNotNull(reviews);
        assertEquals(0, reviews.size(), "Review with no message should not be added");
    }

    @Test
    void Add_Review_NoRating_Returns_No_Rating() {
        // Arrange
        String userName = "Excellent product";
        String picture = "FakeURL";
        String comment = "Super !";
        int rate = 0;
        restaurantApi.resetReview();

        // Act
        detailsViewModel.addReview(userName, picture, comment, rate);
        Deque<Review> reviews = detailsViewModel.getReviews().getValue();


        // Assert
        assertNotNull(reviews);
        assertEquals(0, reviews.size(), "Review with no rating should not be added");
    }





}
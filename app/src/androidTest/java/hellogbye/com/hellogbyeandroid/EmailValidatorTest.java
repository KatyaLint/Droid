//package hellogbye.com.hellogbyeandroid;
//
//import hellogbye.com.hellogbyeandroid.utilities.EmailValidator;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.CoreMatchers.*;
//import static org.mockito.Mockito.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
///**
// * Created by arisprung on 10/14/15.
// */
//public class EmailValidatorTest {
//
//    @Test
//    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
//        assertTrue(EmailValidator.isValidEmail("name@email.com"));
//    }
//
//    @Test
//    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
//        assertTrue(EmailValidator.isValidEmail("name@email.co.uk"));
//    }
//
//    @Test
//    public void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
//        assertFalse(EmailValidator.isValidEmail("name@email"));
//    }
//
//    @Test
//    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
//        assertFalse(EmailValidator.isValidEmail("name@email..com"));
//    }
//
//    @Test
//    public void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
//        assertFalse(EmailValidator.isValidEmail("@email.com"));
//    }
//
//    @Test
//    public void emailValidator_EmptyString_ReturnsFalse() {
//        assertFalse(EmailValidator.isValidEmail(""));
//    }
//
//    @Test
//    public void emailValidator_NullEmail_ReturnsFalse() {
//        assertFalse(EmailValidator.isValidEmail(null));
//    }
//}

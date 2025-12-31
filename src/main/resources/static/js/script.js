// Password Toggle Function
function togglePassword(inputId, button) {
    const input = document.getElementById(inputId);
    const icon = button.querySelector('i');
    
    if (input.type === 'password') {
        input.type = 'text';
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
    } else {
        input.type = 'password';
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
    }
}

$(function() {
    var $userRegister = $("#userRegister");

    // User Register validation
    $userRegister.validate({
        rules: {
            name: { required: true, lettersonly: true },
            email: { required: true, space: true, email: true },
            mobile: { required: true, space: true, numberisOnly: true, minlength: 10, maxlength: 12 },
            password: { required: true, space: true },
            confirmPassword: { required: true, space: true, equalTo: '#password' },
            address: { required: true, all: true },
            city: { required: true, space: true },
            state: { required: true },
            pincode: { required: true, space: true, numberisOnly: true }
        },
        messages: {
            name: { required: 'Name is required', lettersonly: 'Invalid name' },
            email: { required: 'Email is required', space: 'Space not allowed', email: 'Invalid email' },
            mobile: { required: 'Mobile is required', space: 'Space not allowed', numberisOnly: 'Invalid number', minlength: 'Min 10 digits', maxlength: 'Max 12 digits' },
            password: { required: 'Password is required', space: 'Space not allowed' },
            confirmPassword: { required: 'Confirm password', space: 'Space not allowed', equalTo: 'Passwords do not match' },
            address: { required: 'Address is required', all: 'Invalid address' },
            city: { required: 'City is required', space: 'Space not allowed' },
            state: { required: 'State is required' },
            pincode: { required: 'Pincode is required', space: 'Space not allowed', numberisOnly: 'Invalid pincode' }
        }
    });

    // Orders Validation
    var $orders = $("#orders");
    $orders.validate({
        rules: {
            firstName: { required: true, lettersonly: true },
            lastName: { required: true, lettersonly: true },
            email: { required: true, space: true, email: true },
            mobile: { required: true, space: true, numberisOnly: true, minlength: 10, maxlength: 12 },
            address: { required: true, all: true },
            city: { required: true, space: true },
            state: { required: true },
            pincode: { required: true, space: true, numberisOnly: true },
            paymentType: { required: true }
        },
        messages: {
            firstName: { required: 'First name required', lettersonly: 'Invalid name' },
            lastName: { required: 'Last name required', lettersonly: 'Invalid name' },
            email: { required: 'Email required', space: 'Space not allowed', email: 'Invalid email' },
            mobile: { required: 'Mobile required', space: 'Space not allowed', numberisOnly: 'Invalid number', minlength: 'Min 10 digits', maxlength: 'Max 12 digits' },
            address: { required: 'Address required', all: 'Invalid' },
            city: { required: 'City required', space: 'Space not allowed' },
            state: { required: 'State required' },
            pincode: { required: 'Pincode required', space: 'Space not allowed', numberisOnly: 'Invalid pincode' },
            paymentType: { required: 'Select payment type' }
        }
    });

    // Reset Password Validation
    var $resetPassword = $("#resetPassword");
    $resetPassword.validate({
        rules: {
            password: { required: true, space: true },
            confirmPassword: { required: true, space: true, equalTo: '#pass' }
        },
        messages: {
            password: { required: 'Password required', space: 'Space not allowed' },
            confirmPassword: { required: 'Confirm password', space: 'Space not allowed', equalTo: 'Passwords do not match' }
        }
    });
});

// Custom validators
jQuery.validator.addMethod('lettersonly', function(value, element) {
    return /^[^-\s][a-zA-Z_\s-]+$/.test(value);
});

jQuery.validator.addMethod('space', function(value, element) {
    return /^[^-\s]+$/.test(value);
});

jQuery.validator.addMethod('all', function(value, element) {
    return /^[^-\s][a-zA-Z0-9_,.\s-]+$/.test(value);
});

jQuery.validator.addMethod('numberisOnly', function(value, element) {
    return /^[0-9]+$/.test(value);
});

class MessageMapper:
    """
    Maps an error (or success) code to a string
    """
    CODES = {
        # ADMIN MESSAGES
        0: "Registration Success",
        1: "The username {USERNAME} already exists!",
        2: "The email address {EMAIL} is already associated with another "
           "account. Did you mean to log in?",
        3: "That phone number is already associated with another "
           "account. Did you mean to log in?",
        4: "The email address {EMAIL} does not exist, did you mean to sign up?",
        5: "The username {USERNAME} does not exist, did you mean to sign up?",
        6: "Your password was incorrect, please try again.",
        7: "Login success",
        8: "Email successfully changed",
        9: "First name successfully changed",
        10: "Last name successfully changed",
        11: "Password successfully changed",
        12: "Phone number successfully changed",
        13: "Profile picture successfully changed",
        # SERVICE MESSAGES

        # USER MESSAGES
    }


def getMessage(code):
    try:
        return MessageMapper.CODES[code]
    except KeyError:
        return ""

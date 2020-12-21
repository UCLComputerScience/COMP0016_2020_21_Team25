class AdminModel:

    @staticmethod
    def register(database, username, password, first_name,
                 last_name, email, phone_number):
        code = AdminModel._check_existing(database, username, email, phone_number)
        if code == 4:
            query = "INSERT INTO ADMIN (USERNAME, PASSWORD, FIRST_NAME, LAST_NAME," \
                    "EMAIL, PHONE_NUMBER) VALUES ('{}', '{}', '{}', '{}', '{}', '{}')"
            database.execute(query.format(username, password, first_name, last_name,
                                          email, phone_number))
            return 0
        return code

    @staticmethod
    def _check_existing(database, username, email, phone_number):
        if AdminModel.username_exists(database, username):
            return 1
        elif AdminModel.email_exists(database, email):
            return 2
        elif AdminModel.phone_number_exists(database, phone_number):
            return 3
        return 4

    @staticmethod
    def username_exists(database, username):
        username_query = "SELECT USERNAME FROM ADMIN WHERE USERNAME " \
                         "= '" + username + "'"
        return len(database.execute(username_query)) != 0

    @staticmethod
    def email_exists(database, email):
        email_query = "SELECT EMAIL FROM ADMIN WHERE EMAIL='" + email + "'"
        return len(database.execute(email_query)) != 0

    @staticmethod
    def phone_number_exists(database, phone_number):
        phone_number_query = "SELECT PHONE_NUMBER FROM ADMIN WHERE " \
                             "PHONE_NUMBER = '" + phone_number + "'"
        return len(database.execute(phone_number_query)) != 0

    @staticmethod
    def login(database, username_or_email, password):
        username = AdminModel.get_username_by_email(database, username_or_email)
        is_email = True
        if username == "":
            is_email = False
            username = username_or_email
        user_password = AdminModel.get_password_by_username(database, username)
        if user_password == "":
            if is_email:
                return 4
            return 5
        if user_password != password:
            return 6
        return 7

    @staticmethod
    def get_username_by_email(database, email):
        query = "SELECT USERNAME FROM ADMIN WHERE EMAIL='" + email + "'"
        result = database.execute(query)
        if len(result) == 0:
            return ""
        return result[0]["USERNAME"]

    @staticmethod
    def get_email_by_username(database, username):
        return AdminModel._get_attr_by_username(database, 'EMAIL', username)

    @staticmethod
    def get_password_by_username(database, username):
        return AdminModel._get_attr_by_username(database, 'PASSWORD', username)

    @staticmethod
    def get_first_name_by_username(database, username):
        return AdminModel._get_attr_by_username(database, 'FIRST_NAME', username)

    @staticmethod
    def get_last_name_by_username(database, username):
        return AdminModel._get_attr_by_username(database, 'LAST_NAME', username)

    @staticmethod
    def get_phone_number_by_username(database, username):
        return AdminModel._get_attr_by_username(database, 'PHONE_NUMBER', username)

    @staticmethod
    def get_profile_picture_by_username(database, username):
        return AdminModel._get_attr_by_username(database, 'PICTURE_ID', username)

    @staticmethod
    def _get_attr_by_username(database, attribute_name, username):
        query = "SELECT PASSWORD FROM ADMIN WHERE {ATTRIBUTE}='" + username + "'"
        result = database.execute(query.format(ATTRIBUTE=attribute_name))
        if len(result) == 0:
            return ""
        return result[0][attribute_name]

    @staticmethod
    def change_email(database, username, entered_email):
        AdminModel.change_attribute(database, username, "EMAIL", entered_email)
        return 8

    @staticmethod
    def change_first_name(database, username, entered_name):
        AdminModel.change_attribute(database, username, "FIRST_NAME", entered_name)
        return 9

    @staticmethod
    def change_last_last_name(database, username, entered_name):
        AdminModel.change_attribute(database, username, "LAST_NAME", entered_name)
        return 10

    @staticmethod
    def change_password(database, username, entered_password):
        AdminModel.change_attribute(database, username, "PASSWORD", entered_password)
        return 11

    @staticmethod
    def change_phone_number(database, username, entered_number):
        AdminModel.change_attribute(database, username, "PHONE_NUMBER", entered_number)
        return 12

    @staticmethod
    def change_profile_picture(database, username, profile_pic_id):
        command = "UPDATE ADMIN SET PICTURE_ID=" + profile_pic_id +\
                  " WHERE USERNAME='" + username + "'"
        database.execute(command)
        return 13

    @staticmethod
    def change_attribute(database, username, attribute_name, new_value):
        command = "UPDATE ADMIN SET {ATTRIBUTE}='" + \
                  new_value + "' WHERE USERNAME='" + username + "'"
        database.execute(command.format(ATTRIBUTE=attribute_name))

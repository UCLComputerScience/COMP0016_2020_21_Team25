from database.models.AdminModel import AdminModel
from database.models.DatabaseModel import DatabaseModel
from database.util.MessageMapper import getMessage


class MainController:
    INSTANCE = None

    def __init__(self):
        self._database = DatabaseModel.instance()

    @staticmethod
    def instance():
        if MainController.INSTANCE is None:
            MainController.INSTANCE = MainController()
        return MainController.INSTANCE

    """ ADMIN INTERFACE """
    def get_username_by_email(self, email):
        return AdminModel.get_username_by_email(self._database, email)

    def get_email_by_username(self, username):
        return AdminModel.get_email_by_username(self._database, username)

    def get_password_by_username(self, username):
        return AdminModel.get_password_by_username(self._database, username)

    def get_first_name_by_username(self, username):
        return AdminModel.get_first_name_by_username(self._database, username)

    def get_last_name_by_username(self, username):
        return AdminModel.get_last_name_by_username(self._database, username)

    def get_phone_number_by_username(self, username):
        return AdminModel.get_phone_number_by_username(self._database, username)

    def get_profile_picture_by_username(self, username):
        return AdminModel.get_profile_picture_by_username(self._database, username)

    def username_exists(self, username):
        return AdminModel.username_exists(self._database, username)

    def email_exists(self, email):
        return AdminModel.email_exists(self._database, email)

    def phone_number_exists(self, phone_number):
        return AdminModel.phone_number_exists(self._database, phone_number)

    def register(self, username, password, first_name, last_name, email, phone_number):
        code = AdminModel.register(self._database, username, password, first_name,
                                   last_name, email, phone_number)
        if code == 1:
            return getMessage(code).format(USERNAME=username)
        elif code == 2:
            return getMessage(code).format(EMAIL=email)
        return getMessage(code)

    def login(self, username_or_email, password):
        code = AdminModel.login(self._database, username_or_email, password)
        if code == 4:
            return getMessage(code).format(EMAIL=username_or_email)
        elif code == 5:
            return getMessage(code).format(USERNAME=username_or_email)
        return getMessage(code)

    def change_email(self, username, new_email):
        code = AdminModel.change_email(self._database, username, new_email)
        return getMessage(code)

    def change_first_name(self, username, new_name):
        code = AdminModel.change_first_name(self._database, username, new_name)
        return getMessage(code)

    def change_last_name(self, username, new_name):
        code = AdminModel.change_last_last_name(self._database, username, new_name)
        return getMessage(code)

    def change_password(self, username, new_password):
        code = AdminModel.change_password(self._database, username, new_password)
        return getMessage(code)

    def change_phone_number(self, username, new_phone_number):
        code = AdminModel.change_phone_number(self._database, username, new_phone_number)
        return getMessage(code)

    def change_profile_picture(self, username, new_pic_id):
        code = AdminModel.change_profile_picture(self._database, username, new_pic_id)
        return getMessage(code)

    """ SERVICE INTERFACE """

    """ USER INTERFACE """

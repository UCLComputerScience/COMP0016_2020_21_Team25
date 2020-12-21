from database.MainController import MainController

if __name__ == "__main__":
    instance = MainController.instance()
    print(instance.login("Ernest", "12345"))
    print()

    print(instance.login("ernest.nkansah-badu.19@ucl.ac.uk", "12345"))
    print()

    # Valid
    print(instance.register("Ernest", "12345", "Ernest", "Badu",
                            "ernest.nkansah-badu.19@ucl.ac.uk", "11111111111"))
    print()

    # Duplicate username
    print(instance.register("Ernest", "12345", "Ernest", "Badu",
                            "you@domain.co.uk", "222222222222"))
    print()

    # Duplicate email
    print(instance.register("Ernest1", "12345", "Ernest", "Badu",
                            "ernest.nkansah-badu.19@ucl.ac.uk", "222222222222"))
    print()

    # Duplicate phone number
    print(instance.register("Ernest1", "12345", "Ernest", "Badu",
                            "you@domain.co.uk", "11111111111"))
    print()

    print(instance.login("Ernest", "12345"))
    print()

    print(instance.login("Ernest", "11111"))
    print()

    print(instance.login("ernest.nkansah-badu.19@ucl.ac.uk", "12345"))
    print()

    print(instance.login("ernest.nkansah-badu.19@ucl.ac.uk", "11111"))
    print()

    print(instance.login("aaaa", "12345"))
    print()

    print(instance.login("aaaa", "11111"))
    print()

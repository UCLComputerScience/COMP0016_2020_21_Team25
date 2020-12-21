import ibm_db


class DatabaseModel:
    INSTANCE = None

    def __init__(self):
        self._connection = self._connect()

    @staticmethod
    def instance():
        """ Factory Pattern to enforce a singleton database connection """
        if DatabaseModel.INSTANCE is None:
            DatabaseModel.INSTANCE = DatabaseModel()
        return DatabaseModel.INSTANCE

    def _connect(self):
        """ Connect to the database hosted on IBM Cloud """
        try:
            return ibm_db.connect('DATABASE=BLUDB;'
                                  'HOSTNAME=dashdb-txn-sbox-yp-lon02-15.services.eu-gb.bluemix.net;'
                                  'PORT=50000;'
                                  'PROTOCOL=TCPIP;'
                                  'UID=fpd17536;'
                                  'PASSWORD=9rd88vq8k75^ktmc;',
                                  '', '')
        except Exception as e:
            print("Could not connect:", e, ibm_db.conn_errormsg())
        finally:
            pass
        return None

    def execute(self, command):
        """  Execute a command on the database and return the result if it is a query """
        result_set = []
        try:
            statement = ibm_db.exec_immediate(self._connection, command)
            result = ibm_db.fetch_both(statement)
            while result:
                result_set.append(result)
                result = ibm_db.fetch_both(statement)
        except Exception as e:
            print("Transaction could not be completed:", e, ibm_db.stmt_errormsg())
        finally:
            pass
        return result_set

    def execute_many(self, commands):
        """ Execute a list of command strings and return a 2d array where each
        inner array represents the result set of one command """
        result_set = []
        for command in commands:
            result_set.append(self.execute(command))
        return result_set
from datetime import datetime


def deposit(info):
    amount = int(input('Enter amount to deposit: '))
    old_balance = int(info[4])  # balance of the list
    new_balance = old_balance + amount  # update balance
    # info[-1]=str(new_balance) # update balance in the list
    # new_data='\t'.join(info)
    try:
        customerFile = open('Customer.txt', 'r')
    except:
        print('File not found!!')
        exit()
    else:
        # loop through each line in the file
        # save data in old_data
        data = customerFile.read()
        old_data = data.splitlines()

        # create empty list of new_data
        new_data = []

        # loop through old_data
        for i in old_data:
            # match info[0] ie. Account id of customer with every cutomer's id in each line
            if info[0] in i:
                # match found then update the balance
                line = i.replace(str(old_balance), str(new_balance))
                # append the updated customer data back into new_data
                new_data.append(line)
            # if customer id doesn't matches, simply append to the new_data (we don't want to change other customer's data)
            else:
                new_data.append(i)
        customerFile.close()  # close the file

        # open again the file in write mode
        customerFile = open('Customer.txt', 'w')

        # loop through the new_data and write to the file
        for element in new_data:
            customerFile.write(element + '\n')
        customerFile.close()

        # opening transactions file
        transactionFile = open('Transactions.txt', 'a')

        now = datetime.now()  # current date and time
        date = now.strftime('%d-%m-%Y')  # extract date and convert to string

        # creating data to store in the file in order- accid, date, oldbalance, new balance, deposit/withdraw
        data = info[0] + '\t' + date + '\t' + str(old_balance) + '\t' + str(new_balance) + '\t' + 'Deposit' + '\n'

        # write data to transactions file
        transactionFile.write(data)

        # close the file
        transactionFile.close()

        print('Deposited sucessfully')
        checkBalance(info)
        menu()


def withdraw(info):
    amount = int(input('Enter amount to withdraw: '))

    old_balance = int(info[4])  # balance of the list
    if info[5] == 'Savings':
        min_balance = 100
    else:
        min_balance = 500

    while (old_balance < amount or (old_balance - amount) < min_balance):
        print('You have insufficient funds. Please try a lower amount or check your balance.')
        amount = int(input('Enter amount to withdraw or Press 0 to check your balance: '))
        if amount == 0:
            checkBalance(info)
            withdraw(info)

    new_balance = old_balance - amount  # update balance in the list

    try:
        customerFile = open('Customer.txt', 'r')
    except:
        print('File not found!!')
        exit()
    else:
        # loop through each line in the file
        # save data in old_data
        data = customerFile.read()
        old_data = data.splitlines()

        # create empty list of new_data
        new_data = []

        # loop through old_data
        for i in old_data:
            # match info[0] ie. Account id of customer with every cutomer's id in each line
            if info[0] in i:
                # match found then update the balance
                line = i.replace(str(old_balance), str(new_balance))
                # append the updated customer data back into new_data
                new_data.append(line)
            # if customer id doesn't matches, simply append to the new_data (we don't want to change other customer's data)
            else:
                new_data.append(i)
        customerFile.close()  # close the file

        # open again the file in write mode
        customerFile = open('Customer.txt', 'w')

        # loop through the new_data and write to the file
        for element in new_data:
            customerFile.write(element + '\n')
        customerFile.close()

        # opening transactions file
        transactionFile = open('Transactions.txt', 'a')

        now = datetime.now()  # current date and time
        date = now.strftime('%d-%m-%Y')  # extract date and convert to string

        # creating data to store in the file in order- accid, date, oldbalance, new balance, deposit/withdraw
        data = info[0] + '\t' + date + '\t' + str(old_balance) + '\t' + str(new_balance) + '\t' + 'Withdraw' + '\n'

        # write data to transactions file
        transactionFile.write(data)

        # close the file
        transactionFile.close()

        print('Withdrawal Successful')
        checkBalance(info)
        menu()


def changePassword(info):
    old_password = info[1]
    password = input('Enter your new password: ')

    try:
        customerFile = open('Customer.txt', 'r')
    except:
        print('File not found!!')
        exit()
    else:
        # loop through each line in the file
        # save data in old_data
        data = customerFile.read()
        old_data = data.splitlines()

        # create empty list of new_data
        new_data = []

        # loop through old_data
        for i in old_data:
            # match info[0] ie. account ID of customer with every cutomer's id in each line
            if info[0] in i:
                # match found then update the password
                line = i.replace(old_password, password)
                # append the updated customer data back into new_data
                new_data.append(line)
            # if customer id doesn't matches, simply append to the new_data (we don't want to change other customer's data)
            else:
                new_data.append(i)
        customerFile.close()  # close the file

        # open again the file in write mode
        customerFile = open('Customer.txt', 'w')

        # loop through the new_data and write to the file
        for element in new_data:
            customerFile.write(element + '\n')
        customerFile.close()

        print('Password changed sucessfully')

        # checkBalance(info)
        print('Thank you for using Our Bank. Have a great day!')
        exit()


def checkBalance(info):
    try:
        customerFile = open('Customer.txt', 'r')
    except:
        print('File not found!!')
        exit()
    else:
        for acc in customerFile:
            data = acc.split()
            if info[0] in data:
                print('Your balance is: RM ', data[4])
        customerFile.close()



def incrementor():
    # initialize dict with number 1 less than sequence start
    i = {'count': 0}

    # create another function inside (nested function)
    def number():
        # increment count
        i['count'] += 1

        # return key's value(id)
        return i['count']

    return number


def addAdmin():
    admin_username = input('Admin username: ')
    admin_password = input('Admin password: ')

    try:
        adminFile = open('Admin.txt', 'a')
    except:
        print('File not found!')
        exit()
    else:

        data = admin_username + '\t' + admin_password + '\n'
        adminFile.write(data)
        adminFile.close()
        print('Admin account successfully created!')
        suMenu()


def addCustomer():
    # ask for customer details
    first_name = input('First name: ')
    last_name = input('Last name: ')
    email = input('Email: ')
    address = input('Address: ')

    default_password = first_name[:4] + last_name[:4]

    acc_id = number()
    # convert to string
    acc_id = str(acc_id)  # converts to string and stores in the same variable.
    acc_id = acc_id.zfill(4)  # fill string with 0's on left side , length of new string should be 4

    # ask whether savings or current account
    i = int(input('Choose 1 for Savings or 2 for Current: '))
    if i == 1:
        acc_type = 'Savings'
        min_balance = 100
    else:
        acc_type = 'Current'
        min_balance = 500

    starting_balance = int(input('Start Balance: '))

    # ensuring that starting balance is more than min balance
    while (starting_balance < min_balance):
        starting_balance = int(input('Starting balance should be more than minimum balance. Enter Start Balance: '))

    # merge all the data into a string data
    data = acc_id + '\t' + default_password + '\t' + first_name + '\t' + last_name + '\t' + str(
        starting_balance) + '\t' + str(acc_type) + '\t' + email + '\t' + address + '\n'
    try:
        customerFile = open('Customer.txt', 'a')
    except:
        print('File not found!!')
        exit()
    else:

        # write to file
        customerFile.write(data)
        print('New Customer Added !!!')
        customerFile.close()
    adminMenu()


def updateCustomer():
    acc = input('Enter Customer ID: ')

    print('Choose what detail to update: ')
    print('1. Address')
    print('2. Email')
    print('3. Exit')
    choice = int(input('Select Service: '))

    if choice == 3:
        exit()

    try:
        customerFile = open('Customer.txt', 'r')
    except:
        print('File not found!!')
        exit()
    else:
        # loop through each line in the file
        # save data in old_data
        data = customerFile.read()
        old_data = data.splitlines()

        # create empty list of new_data
        new_data = []

        # loop through old_data
        for i in old_data:
            if acc in i:
                info = list(i.split('\t'))
                if choice == 1:
                    # extract index 7 onwards of customer info and convert to a string
                    old_details = ''.join(info[7:])
                    new_details = input('Enter address: ')
                if choice == 2:
                    old_details = info[6]
                    new_details = input('Enter email: ')
                line = i.replace(old_details, new_details)
                new_data.append(line)
            else:
                new_data.append(i)

        customerFile = open('Customer.txt', 'w')

        for element in new_data:
            customerFile.write(element + '\n')
        customerFile.close()

        print('Customer details updated successfully')
        adminMenu()


def suMenu():
    print('Choose a service')
    print('1. Create admin account')
    print('2. Back to Main Menu')
    choice = int(input('Select service: '))

    if choice == 1:
        addAdmin()
    elif choice == 2:
        menu()
    else:
        print('Invalid Input. Happy Banking!!')
        suMenu()


def displayTransactions():
    start_date = input('Enter start date in DD-MM-YYYY format: ')
    end_date = input('Enter end date in DD-MM-YYYY format: ')

    # convert to date objects
    date1 = datetime.strptime(start_date, '%d-%m-%Y')
    date2 = datetime.strptime(end_date, '%d-%m-%Y')

    # counters
    w = 0
    d = 0

    try:
        transactionFile = open('Transactions.txt', 'r')

    except:
        print('File not found')
        exit()
    else:
        for acc in transactionFile:
            data = acc.split()
            cd = datetime.strptime(data[1], '%d-%m-%Y')

            if cd >= date1 and cd <= date2:
                print(data)
                if 'Withdraw' in data:
                    w += 1
                else:
                    d += 1
        transactionFile.close()

        print('Total Withdrawals: ', w)
        print('Total Deposits: ', d)


def adminMenu():
    print('Choose a service')
    print('1. Create customer account')
    print('2. Update Customer account')
    print('3. Display Transaction History')
    print('4. Exit')
    choice = int(input('Select service: '))

    if choice == 1:
        addCustomer()
    elif choice == 2:
        updateCustomer()
    elif choice == 3:
        displayTransactions()
    elif choice == 4:
        exit()
    else:
        print('Invalid Input, please try again')
        adminMenu()


def history(info):
    # take dates as user input( string)
    start_date = input('Enter start date in DD-MM-YYYY format: ')
    end_date = input('Enter end date in DD-MM-YYYY format: ')

    # convert those dates into date objects using strptime
    date1 = datetime.strptime(start_date, '%d-%m-%Y')
    date2 = datetime.strptime(end_date, '%d-%m-%Y')

    # take counters for withdrawal and deposits
    w = 0
    d = 0

    try:
        transactionFile = open('Transactions.txt', 'r')

    except:
        print('File not found')
        exit()
    else:
        #go through file line by line
        for acc in transactionFile:
            # split each line and store in data (list type)
            data = acc.split()
            cd = datetime.strptime(data[1], '%d-%m-%Y')
            # match id -->will ensure that only his own details will be shown
            if info[0] in data:

                # check if cd(transaction date) falls between date1 and date2
                if cd >= date1 and cd <= date2:
                    # if it falls between the dates, print the transaction line
                    print(data)
                    # check if it is withdraw, if yes update withdraw counter or else update deposit counter
                    if 'Withdraw' in data:
                        w += 1
                    else:
                        d += 1

        transactionFile.close()

        print('Total Withdrawals for the given duration : ', w)
        print('Total Deposits for the given duration: ', d)


def customerMenu(login_info):
    print('Choose a service')
    print('1. Deposit')
    print('2. Withdraw')
    print('3. Change password')
    print('4. Check balance')
    print('5. View Transaction History')
    print('6. Exit')
    choice = int(input('Select service: '))

    if choice == 1:
        deposit(login_info)
    elif choice == 2:
        withdraw(login_info)
    elif choice == 3:
        changePassword(login_info)
    elif choice == 4:
        checkBalance(login_info)
    elif choice == 5:
        history(login_info)
    elif choice == 6:
        exit()
    else:
        print('Invalid Input. Happy Banking!!')
        customerMenu(login_info)


def suLogin():
    user = input('Enter id: ')
    password = input('Enter password: ')
    login = False

    try:
        suFile = open('Superuser.txt', 'r')
    except:
        print('File not found!!')
        exit()
    else:
        for acc in suFile:
            login_info = acc.split()
            if user == login_info[0] and password == login_info[1]:
                print('Welcome ', login_info[0])
                login = True
                break
        if login == True:
            suMenu()
        else:
            print('invalid Credentials!!!')
            menu()
        suFile.close()


def adminLogin():
    user = input('Enter id: ')
    password = input('Enter password: ')
    login = False

    try:
        adminFile = open('Admin.txt', 'r')
    except:
        print('File not found!!')
        exit()
    else:
        for acc in adminFile:
            login_info = acc.split()
            if user == login_info[0] and password == login_info[1]:
                print('Welcome ', login_info[0], '!!!')
                login = True
                break

        if login == True:
            print('Welcome ', user)
            adminMenu()
        else:
            print('Invalid Credentials!!!')
            menu()
        adminFile.close()


def customerLogin():
    user = input('Enter id: ')
    password = input('Enter password: ')
    login = False

    try:
        customerFile = open('Customer.txt', 'r')
    except:
        print('File not found!!')
        exit()
    else:
        for acc in customerFile:
            login_info = acc.split()
            if user == login_info[0] and password == login_info[1]:
                print('Welcome', login_info[2], login_info[3], '!!!')
                login = True
                break

        if login == True:
            customerMenu(login_info)
        else:
            print('invalid Credentials!!!')
            menu()
        customerFile.close()


def menu():
    print('1. Admin Login')
    print('2. Customer Login')
    print('3. Super User Login')
    print('4. Exit')
    choice = int(input('Enter your choice: '))
    if choice == 1:
        adminLogin()
    elif choice == 2:
        customerLogin()
    elif choice == 3:
        suLogin()
    else:
        print('Invalid Input. Happy Banking!!')
        exit()


number = incrementor()
menu()
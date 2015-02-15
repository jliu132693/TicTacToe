from handler import *


class LoginPage(Handler):
    def get(self):
        self.render('login.html')

    def post(self):
        username = self.request.get('username')
        password = self.request.get('password')

        user = User.login(username, password)

        if user:
            self.login(user)
            self.redirect('/')
        else:
            self.error(400)
            self.render('login.html', errorValue="Invalid input.")

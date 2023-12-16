from selenium import webdriver
from selenium.webdriver.common.by import By


class DownloaderBotUtil:
    def __init__(self, driver: webdriver):
        self.driver = driver

    def login(self, username: str, password: str, sleepFunction):
        usernameBox = self.get_element_by_id('username')
        usernameBox.send_keys(username)

        passwordBox = self.get_element_by_id('password')
        passwordBox.send_keys(password)

        sleepFunction(0.5)

        loginButton = self.get_element_by_id('loginbtn')
        loginButton.click()

        sleepFunction(0.5)

    def change_route_to_save(self, route: str):
        self.driver.command_executor._commands["send_command"] = (
            "POST",
            '/session/$sessionId/chromium/send_command'
        )
        self.driver.execute("send_command", {
            "cmd": "Page.setDownloadBehavior",
            "params": {
                "behavior": "allow",
                "downloadPath": route
            }
        })

    def get_div_file_container(self):
        return list(map(lambda x: self.get_div_file_container_by_id(*x), enumerate(self.get_students())))

    def get_div_file_container_by_id(self, id: int, student):
        return student.find_element(By.ID, f'mod_assign_grading_r{id}_c8')

    def get_students(self):
        return list(map(self.get_student, range(0, 10)))

    def get_student(self, id: int):
        return self.driver.find_element(By.ID, f'mod_assign_grading_r{id}')

    def get_pages(self):
        return list(map(self.get_href, self.driver.find_element(By.CLASS_NAME, 'paging').find_elements(
            By.TAG_NAME, 'a')))

    def get_href(self, element):
        return element.get_attribute('href')

    def open_link(self, link: str):
        self.driver.get(link)

    def open_href_by_tag_a(self, element):
        self.open_link(self.get_href(element.find_element(
            By.TAG_NAME, 'a')))

    def get_element_by_id(self, id: str):
        return self.driver.find_element(By.ID, id)

    def exit(self):
        self.driver.close()
        self.driver.quit()

import sys
import time
import json

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.edge.service import Service
from selenium.common.exceptions import NoSuchElementException

from downloader_bot_util import DownloaderBotUtil

# ENTORNO DE PRUEBAS


# Variables de configuracion

if len(sys.argv) < 4:
    print("Argumentos incompletos, se requiere numero de laboratorio, ruta web base y ruta credenciales.")
    sys.exit(1)

print(sys.argv)

#   System Args:
#       1: Numero de laboratorio
#       2: Ruta web base
#       3: Ruta al .json de Credenciales

# Lab number
lab_number = sys.argv[1]

# User credentials
with open(sys.argv[3], 'r') as file:
    credentials = json.load(file)

route_to_save = fr'C:\Users\yoloo\Desktop\proyecto_final_leng\Lab_{lab_number}_Entregas_(pendientes)'

uri_base = f'https://campusvirtual.ufro.cl/mod/assign/view.php?id=1610488&action=grading'

uri = f'https://campusvirtual.ufro.cl/mod/assign/view.php?id={1610488 + ((int(lab_number) - 1) * 4)}&action=grading'

if sys.argv[2] != uri_base:
    uri = sys.argv[2]

# Config

service = Service("./msedgedriver.exe")
options = webdriver.EdgeOptions()
options.add_experimental_option("detach", True)
options.add_experimental_option("prefs", {
    "download.default_directory": route_to_save,
    "download.prompt_for_download": False,
    "download.directory_upgrade": True,
    "safebrowsing.enabled": True
})

botUtil = DownloaderBotUtil(
    webdriver.Edge(service=service, options=options))

botUtil.open_link(uri)

# Login

botUtil.login(credentials.get('username'),
              credentials.get('password'), time.sleep)

# Counter pages

pages = botUtil.get_pages()

print(f'Hay {pages.__len__()} paginas')

# Download Elements

group = 1

for page in range(0, pages.__len__()):
    print(f'Pagina {page+1} de {pages.__len__()}')

    students = botUtil.get_students()
    divFileContainer = botUtil.get_div_file_container()

    for i, div in enumerate(divFileContainer):

        print(f'Comprobando archivos del estudiante {i}')

        try:
            # Contenedor de ambos archivos

            divWithElements = div.find_elements(
                By.CSS_SELECTOR, '.ygtvcell.ygtvhtml.ygtvcontent')

            # Necesito que sean 2 para descargarlos, luego obtieme el link y lo abre

            if divWithElements.__len__() == 2:
                print(f'Estudiante {i} tiene archivos, descargando...')
                for divElement in divWithElements:
                    botUtil.change_route_to_save(
                        route_to_save+fr'\GRUPO_{group}')
                    botUtil.open_href_by_tag_a(divElement)
                group += 1
                time.sleep(0.5)

        except NoSuchElementException:
            print('No hay archivos')
            continue

    students.clear()
    divFileContainer.clear()

    botUtil.open_link(pages[page])
    time.sleep(0.5)

botUtil.exit()
sys.exit(0)

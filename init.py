#!/usr/bin/env python
import subprocess
import requests
import time

with open("/tmp/output.log","a") as out:
    subprocess.Popen("docker compose up", shell=True, stdout=out, stderr=out)

print("Waiting for virtual-smart-home to start")
print("Waiting for virtual-smart-home-gateway to start")
time.sleep(9)
print("Servers started")

house_url = "http://localhost:8081"
gateway_url = "http://localhost:8080"
doorObject = {"label": "door1", "deviceType": "door", "status": "open"}
thermometerObject = {"label": "thermometer1", "deviceType": "thermometer", "temperature": 15.3}
rgbObject = {
    "label": "rgb1", "deviceType": "rgb", "switchedOn": "true", "red": 255, "green": 0, "blue": 0}
fireplaceObject = {"label": "fireplace1", "deviceType": "fireplace", "status": "extinguished"}
houseObject = {"name": "house1", "address": "http://smart-home:8081"}

requests.post(gateway_url + "/api/v0.1/gateway/house", json=houseObject)
requests.post(house_url + "/api/v0.1/house/device/door/", json=doorObject)
requests.post(house_url + "/api/v0.1/house/device/thermometer/", json=thermometerObject)
requests.post(house_url + "/api/v0.1/house/device/rgb/", json=rgbObject)
requests.post(house_url + "/api/v0.1/house/device/fireplace/", json=fireplaceObject)
print("Devices created")
# Proxidize Android (Legacy)

> ⚠️ **Legacy Project Notice**
>
> This repository contains the legacy Proxidize Android application, an archived proof-of-concept app originally created for self-hosted mobile proxy deployments.
>
> This project is no longer Proxidize's primary product offering. Today, Proxidize primarily provides managed cloud **Mobile Proxies** and **Residential Proxies** that do not require customers to deploy, root, or maintain Android devices.
>
> For current Proxidize products, visit:
>
> - Mobile Proxies: https://proxidize.com/proxy-server/mobile-proxies/
> - Residential Proxies: https://proxidize.com/residential-proxies/
> - Developer Docs: https://developers.proxidize.com/
>
> This repository is kept online for historical reference and existing legacy users.

## About This Repository

Proxidize Android Legacy was an experimental Android application created as an early proof of concept for self-hosted mobile proxy infrastructure.

This repository is archived and kept online for historical reference and existing legacy users. It is not recommended for new Proxidize customers.

For current proxy deployments, use Proxidize Mobile Proxies or Proxidize Residential Proxies instead.


![Overview](https://i.imgur.com/gsRoRBt.png)



## What Is Proxidize?

Proxidize is a managed proxy provider offering Mobile Proxies and Residential Proxies for web scraping, AI data collection, SEO monitoring, market research, price monitoring, automation, and other data-driven workflows.

The legacy Android app in this repository was part of Proxidize's earlier self-hosted proxy infrastructure tooling. It is not the recommended starting point for new customers.


<div align="center"> <a href="https://proxidize.com/">
    <img src="https://i.imgur.com/3FEWrk5.png" height="auto"/>
  </a>
</div>

<div align="center">
  <h2>Proxidize</h2>
  <a href="https://proxidize.com/proxy-server/mobile-proxies/">Mobile Proxies</a>
  <span>&nbsp;&nbsp;•&nbsp;&nbsp;</span>
  <a href="https://proxidize.com/residential-proxies/">Residential Proxies</a>
  <span>&nbsp;&nbsp;•&nbsp;&nbsp;</span>
  <a href="https://developers.proxidize.com/">Developer Docs</a>
  <span>&nbsp;&nbsp;•&nbsp;&nbsp;</span>
  <a href="https://help.proxidize.com/en/proxidize-proxy-builder-legacy">Proxy Builder Legacy Docs</a>
  <br />
  <hr />
</div>

---

## Current Proxidize Products

Proxidize currently focuses on managed cloud proxy services:

- **Mobile Proxies** — Managed 4G/5G mobile proxies using real mobile carrier networks.
- **Residential Proxies** — Managed residential proxy access with global coverage.
- **Proxy Builder (Legacy)** — Self-hosted platform maintained for existing customers.

The Android app in this repository is not part of Proxidize's current managed proxy platform.

---

## Legacy App Capabilities

![image](https://user-images.githubusercontent.com/107770894/190168239-2084da54-9b5a-4ed6-9ab8-3bd21671adf5.png)

This archived Android app previously supported:

- Creating HTTP(S) and SOCKS proxy endpoints from Android-based legacy deployments
- Manual and automatic IP rotation
- Rotation links/API for legacy workflows
- Optional custom tunneling server configuration
- Android 6.0 to Android 12 support

These capabilities are documented for historical reference only. New users should use Proxidize Mobile Proxies or Residential Proxies through the managed cloud platform.


---

## How the Legacy App Worked

Proxidize Android Legacy worked by establishing a connection to a tunneling server through reverse proxy infrastructure, then launching a local HTTP proxy server on the Android device. This architecture is documented here for historical reference only.

<div align="center">
    <img src="https://i.imgur.com/9UAAcx3.png" height="auto"/>
</div>


The application selected a random port between `10000` and `60000`, connected to the client, and created a proxy server using the random port along with a randomly generated username and password.


---

## Table of Contents

- [About This Repository](#about-this-repository)
- [What Is Proxidize?](#what-is-proxidize)
- [Current Proxidize Products](#current-proxidize-products)
- [Legacy App Capabilities](#legacy-app-capabilities)
- [How the Legacy App Worked](#how-the-legacy-app-worked)
- [Legacy Usage Notes](#legacy-usage-notes)
- [Supported Android Versions & Devices](#supported-android-versions--devices)
- [Project Status](#project-status)
- [FAQ](#faq)


---

## Legacy Usage Notes

<div align="center">
    <img src="https://i.imgur.com/ASSDAe2.png" height="auto"/>
</div>


> These instructions are preserved for historical reference and existing legacy users only. They are not recommended for new Proxidize customers.

<details>
<summary>Archived Android app usage instructions</summary>

- Download the legacy Proxidize Android APK file.
- Install the APK on a compatible Android device.
- Open the app and press Connect.
- Copy the generated proxy connection details.

</details>

---

## Rotation/Changing the IP 

Proxidize Android Legacy has built-in rotation. To set it up, you need to set the app as the default assistant in your settings.


### Automatically Changing the IP Address:

<div align="center">
    <img src="https://i.gyazo.com/8c923c64c2996452c78993003e023d94.png" height="auto"/>
</div>

Proxidize Android Legacy allows you to set a rotation/IP change interval. To use it, you need to:
- Press "AUTO CHANGE IP" button on the home page.
- Select the rotation interval you wish to use.
- Select a time in minutes. Anything less than 30 minutes will harm your phone.
- Press "SET" and your settings will be applied.



### Changing the IP Manually:

<div align="center">
    <img src="https://i.gyazo.com/ad6fa87d163262908253e99cf2f436ab.png" height="auto"/>
</div>


To change the IP address manually, you simply need to press the change IP button.



### Changing the IP via URL/API:

<div align="center">
    <img src="https://i.gyazo.com/ded5e4abaf6f19ca75ff5045889dced4.png" height="auto"/>
</div>

Proxidize Android Legacy generates an IP change link/URL/API that you can use anywhere to change the IP.

To change the IP using the rotation link, you need to:
- Copy the change IP URL under "IP Change Link/API" by pressing the "COPY" button.
- Use the link anywhere or send a GET request to it.

A success response is:

```{"response":"success"}```


---
## Supported Android Versions & Devices

Proxidize Android Legacy supports all ```armeabi-v7a``` running ```Android 6.0``` to ```Android 12```

Supported Android API from ```API 23``` to ```API 31```.

Tested devices:

```
All Android 6.0+ phones
Samsung A Series
Samsung S Series
Samsung M Series
Samsung Note Series
Google Pixel
OnePlus
```
---

## Project Status

This repository is archived and no longer actively maintained by Proxidize.

Issues and pull requests may not be reviewed. For current products and support, use:

- Mobile Proxies: https://proxidize.com/proxy-server/mobile-proxies/
- Residential Proxies: https://proxidize.com/residential-proxies/
- Help Center: https://help.proxidize.com/

---

## FAQ

### Is this app still maintained?

No. This repository is archived and kept online for historical reference and existing legacy users.

### Should new customers use this app?

No. New customers should use Proxidize Mobile Proxies or Proxidize Residential Proxies through the managed cloud platform.

### What replaced this project?

Proxidize's current products are managed Mobile Proxies and Residential Proxies. These products do not require customers to deploy Android devices, tunneling servers, or self-hosted infrastructure.

### Is Proxy Builder still available?

Proxy Builder is a legacy self-hosted platform maintained for existing customers.

---


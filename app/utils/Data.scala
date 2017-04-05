package utils

object Data {

  val EVENT = "event"
  val PAGE_VIEW = "pageview"
  val USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36"

  val TRAINING_INOFFICE = "training-inoffice"
  val TRAINING_ONLINE = "training-online"
  val CONTACT_US = "contact-us"

  def getTechPages: List[String] = {
    List(
      "/resources",
      "/tech-stack",
      "/blog"
    )
  }

  def getNonTechPages: List[String] = {
    List(
      "/process",
      "/services",
      "/work"
    )
  }

  def getNAIPS: List[String] = {
    List(
      "45.119.208.0", // US
      "45.127.92.0",
      "45.248.44.0",
      "45.250.72.0",
      "45.251.44.0",
      "45.252.184.0",
      "45.254.244.0",
      "45.255.128.0",
      "60.254.128.0",
      "101.53.160.0",
      "103.75.116.0", // CA
      "103.215.180.0",
      "202.183.64.0",
      "23.16.0.0",
      "23.29.192.0",
      "23.83.208.0",
      "23.83.224.0",
      "23.91.128.0",
      "23.91.160.0",
      "23.91.224.0",
      "24.204.144.0",
      "24.204.160.0",
      "24.204.192.0",
      "24.207.0.0",
      "24.212.0.0",
      "24.212.128.0",
      "24.213.64.0",
      "24.215.0.0",
      "24.215.64.0",
      "24.222.0.0",
      "24.224.128.0",
      "24.225.128.0",
      "24.226.0.0",
      "24.226.128.0",
      "24.230.192.0",
      "24.231.0.0",
      "24.231.64.0",
      "24.235.32.0",
      "24.235.96.0",
      "24.235.128.0",
      "24.239.0.0",
      "24.244.0.0",
      "24.244.64.0",
      "24.244.80.0",
      "24.244.112.0",
      "24.244.192.0",
      "24.245.224.0",
      "24.246.0.0",
      "24.246.64.0",
      "69.165.128.0",
      "69.166.16.0",
      "69.166.112.0",
      "69.168.128.0",
      "69.171.96.0",
      "69.171.128.0",
      "69.172.144.0",
      "69.172.160.0",
      "69.173.32.0",
      "69.176.160.0",
      "69.196.0.0",
      "69.196.64.0",
      "69.196.128.0",
      "70.24.0.0",
      "70.35.160.0",
      "70.35.208.0",
      "70.36.48.0",
      "70.38.0.0",
      "70.48.0.0",
      "70.64.0.0",
      "70.80.0.0",
      "71.7.128.0",
      "71.17.0.0",
      "71.19.0.0",
      "71.19.160.0",
      "71.19.240.0",
      "72.0.64.0",
      "72.0.192.0",
      "72.13.160.0",
      "72.14.160.0",
      "72.15.48.0",
      "72.15.128.0",
      "72.15.144.0",
      "72.25.192.0",
      "72.28.64.0",
      "72.29.224.0",
      "72.38.0.0",
      "72.45.64.0",
      "72.46.80.0",
      "72.46.84.0",
      "72.46.88.0",
      "72.53.0.0",
      "72.53.128.0",
      "72.55.128.0",
      "72.136.0.0",
      "72.172.96.0",
      "72.172.160.0",
      "74.3.128.0",
      "74.12.0.0",
      "74.48.0.0",
      "74.50.160.0",
      "74.50.224.0",
      "74.51.32.0",
      "74.51.48.0",
      "74.56.0.0",
      "74.82.64.0",
      "74.82.192.0",
      "74.85.153.0",
      "74.85.154.0",
      "74.112.40.0",
      "74.112.124.0",
      "74.112.176.0",
      "74.112.188.0",
      "74.112.191.0",
      "74.113.0.0",
      "74.113.12.0",
      "74.113.20.0",
      "74.113.68.0",
      "74.113.72.0",
      "74.113.176.0",
      "74.114.72.0",
      "74.114.100.0",
      "74.114.136.0",
      "74.114.172.0",
      "74.114.208.0",
      "74.115.192.0",
      "74.115.204.0",
      "74.115.220.0",
      "74.116.120.0",
      "74.116.184.0",
      "74.116.216.0",
      "74.117.40.0",
      "74.117.52.0",
      "74.117.140.0",
      "74.117.248.0",
      "74.118.36.0",
      "74.118.52.0",
      "74.118.100.0",
      "74.118.248.0",
      "74.119.0.0",
      "74.119.88.0",
      "74.119.184.0",
      "74.120.4.0",
      "74.120.52.0",
      "74.120.72.0",
      "74.120.76.0",
      "74.120.220.0",
      "74.121.32.0",
      "74.121.120.0",
      "74.121.148.0",
      "74.121.160.0",
      "74.121.244.0",
      "74.122.52.0",
      "74.122.128.0",
      "74.122.208.0",
      "74.122.244.0",
      "74.123.8.0",
      "74.123.68.0",
      "74.123.92.0",
      "74.126.96.0",
      "74.127.192.0",
      "74.198.0.0",
      "74.200.0.0",
      "74.205.160.0",
      "74.205.208.0",
      "74.206.128.0",
      "74.210.0.0",
      "74.210.128.0",
      "74.213.160.0",
      "74.214.128.0",
      "107.161.96.0"
    )
  }

    def getIPS: List[String] = {
    List(
      "14.192.52.0", // IN
      "103.59.252.0",
      "103.60.0.0",
      "103.60.21.0",
      "103.60.28.0",
      "103.60.40.0",
      "103.60.48.0",
      "103.60.52.0",
      "103.60.68.0",
      "103.60.72.0",
      "103.60.76.0",
      "103.60.80.0",
      "103.60.84.0",
      "103.60.100.0",
      "103.60.104.0",
      "103.60.112.0",
      "103.60.116.0",
      "103.60.128.0",
      "103.60.135.0",
      "103.60.136.0",
      "103.60.140.0",
      "1.6.0.0",
      "1.22.0.0",
      "1.38.0.0",
      "1.186.0.0",
      "1.187.0.0",
      "14.1.116.0",
      "14.1.120.0",
      "14.1.124.0",
      "14.96.0.0",
      "14.102.0.0",
      "14.137.128.0", // AUS
      "14.137.192.0",
      "14.192.160.0",
      "14.200.0.0",
      "27.0.64.0",
      "27.32.0.0",
      "27.34.224.0",
      "27.121.64.0",
      "27.121.104.0",
      "45.112.4.0", // GB
      "103.65.204.0",
      "103.69.60.0",
      "119.42.44.0",
      "203.88.100.0",
      "134.128.0.0",
      "147.114.0.0",
      "192.156.169.0",
      "199.103.86.0",
      "204.27.179.0",
      "103.214.228.0", // FR
      "192.136.54.0",
      "199.103.87.0",
      "204.79.136.0",
      "2.0.0.0",
      "5.39.0.0",
      "5.42.160.0",
      "5.44.160.0",
      "31.3.136.0",
      "31.7.248.0",
      "64.110.28.0", // DE
      "66.133.8.0",
      "192.69.234.0",
      "198.176.223.0",
      "199.103.75.0",
      "204.79.128.0",
      "204.231.228.0",
      "209.141.224.0",
      "2.200.0.0",
      "5.1.64.0",
      "1.0.2.0", // CN
      "1.0.8.0",
      "1.0.32.0",
      "1.1.0.0",
      "1.1.2.0",
      "1.1.4.0",
      "1.1.8.0",
      "1.1.9.0",
      "1.1.10.0",
      "1.1.12.0",
      "1.1.16.0",
      "1.1.32.0",
      "1.2.0.0",
      "1.2.2.0",
      "1.2.4.0",
      "1.2.5.0",
      "1.2.6.0",
      "1.2.8.0",
      "1.2.9.0",
      "1.2.10.0",
      "1.2.12.0",
      "1.2.16.0",
      "1.2.32.0",
      "1.2.64.0",
      "1.3.0.0",
      "1.4.1.0",
      "1.4.2.0",
      "1.4.4.0",
      "1.4.5.0",
      "1.4.6.0",
      "1.4.8.0",
      "1.4.16.0",
      "1.4.32.0",
      "1.4.64.0",
      "1.8.0.0",
      "1.10.0.0",
      "1.10.8.0",
      "1.10.11.0",
      "1.10.12.0",
      "1.10.16.0",
      "1.10.32.0",
      "1.10.64.0",
      "1.12.0.0",
      "1.24.0.0",
      "1.45.0.0",
      "1.48.0.0",
      "1.50.0.0",
      "1.51.0.0",
      "1.56.0.0",
      "1.68.0.0",
      "1.80.0.0",
      "1.88.0.0",
      "1.92.0.0",
      "1.94.0.0",
      "1.116.0.0",
      "1.180.0.0",
      "1.184.0.0",
      "1.188.0.0",
      "1.192.0.0",
      "1.202.0.0",
      "1.204.0.0",
      "14.0.0.0",
      "14.0.12.0",
      "14.1.0.0",
      "14.1.24.0",
      "14.1.96.0",
      "14.1.108.0",
      "14.16.0.0",
      "14.102.128.0",
      "14.102.156.0",
      "14.102.180.0",
      "14.103.0.0",
      "14.104.0.0",
      "14.112.0.0",
      "14.130.0.0",
      "14.134.0.0",
      "14.144.0.0",
      "14.192.4.0",
      "14.192.56.0",
      "14.192.60.0",
      "14.192.76.0",
      "14.196.0.0",
      "14.204.0.0",
      "14.208.0.0",
      "27.0.128.0",
      "27.0.132.0",
      "27.0.160.0",
      "27.0.164.0",
      "27.0.188.0",
      "27.0.204.0",
      "27.0.208.0",
      "27.0.212.0",
      "27.8.0.0",
      "27.16.0.0",
      "27.34.232.0",
      "27.36.0.0",
      "27.40.0.0",
      "27.50.40.0",
      "27.50.128.0",
      "27.54.72.0"
    )
  }

}

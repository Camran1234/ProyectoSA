# FIREWALL RULES


# SSH FIREWALL RULE
resource "google_compute_firewall" "ssh" {
  name    = "ssh-develop"
  network = google_compute_network.vpc_network.name 

  allow {
    protocol = "icmp"
  }

  allow {
    protocol = "tcp"
    ports    = ["22"]
  }

  target_tags   = ["develop", "production", "test"]
  source_ranges = ["0.0.0.0/0"]
}

# OTHER  PORTS FIREWALL RULE
resource "google_compute_firewall" "http-https" {
  name    = "http-https-develop"
  network = google_compute_network.vpc_network.name 
  
  allow {
    protocol = "icmp"
  }
  
  allow {
    protocol = "tcp"
    ports    = ["80","443"]
  }

  source_ranges = ["0.0.0.0/0"]
  target_tags   = ["develop", "production", "test"]
}

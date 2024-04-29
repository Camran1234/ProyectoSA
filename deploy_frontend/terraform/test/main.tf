terraform {
  required_providers {
    google = {
      source = "hashicorp/google"
      version = "4.69.1"
    }
  }
}

provider "google" {
  # Configuration options
  credentials = "${file(var.gce_service_account)}" 
  project     = var.project # Replace this with your host project ID in quotes
  region      = var.region
  zone = var.zone
}

# KUBERNETES VPC
resource "google_compute_network" "vpc_network" {
name = "ticket-network-test"
auto_create_subnetworks = false
}

# PUBLIC SUBNET
resource "google_compute_subnetwork" "public-subnetwork" {
name = "ticket-subnetwork-test"
ip_cidr_range = "10.250.0.0/24"
region = var.region
network = google_compute_network.vpc_network.name  
}

# Router and Cloud NAT are required for installing packages from repos
## Create Cloud Router

resource "google_compute_router" "router" {
  project = var.project
  name    = "nat-router-test"
  network = google_compute_network.vpc_network.name 
  region  = var.region
}

## Create Nat Gateway

resource "google_compute_router_nat" "nat" {
  name                               = "dev-router-nat"
  router                             = google_compute_router.router.name
  region                             = var.region
  nat_ip_allocate_option             = "AUTO_ONLY"
  source_subnetwork_ip_ranges_to_nat = "ALL_SUBNETWORKS_ALL_IP_RANGES"

  log_config {
    enable = true
    filter = "ERRORS_ONLY"
  }
}





#  CREATE INSTANCES




resource "google_compute_instance" "frontend_vm" {
  project      = var.project
  zone         = var.zone
  name         = var.vm_name
  machine_type = var.machine_type
  tags         = ["test"]

  boot_disk {
    initialize_params {
      image = var.image
    }
  }

  metadata = {
    sshKeys = "${var.gce_ssh_user}:${file(var.gce_ssh_pub_key_file)}"
  }

  metadata_startup_script = <<-SCRIPT
    #!/bin/bash
    sudo apt update
    sudo apt install -y ansible
  SCRIPT

  network_interface {
    network    = google_compute_network.vpc_network.self_link
    subnetwork = google_compute_subnetwork.public-subnetwork.self_link

    access_config {
      // external IP
      nat_ip = var.static_ip_address
    }
  }
}

output frontend-vm-develop-ip {
  value       = google_compute_instance.frontend_vm.network_interface[0].network_ip
  sensitive   = false
  description = "private ip of the frontend-develop-vm"
}

output frontend-vm-develop-ipexternal {
  value       = google_compute_instance.frontend_vm.network_interface[0].access_config[0].nat_ip
  sensitive   = false
  description = "external public ip of the frontend-develop-vm"
}
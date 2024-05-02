vm_name = "test-vm"
region = "us-central1"
zone =   "us-central1-a"
project = "proyecto-sa-421708"
machine_type = "e2-standard-2"
image = "ubuntu-os-cloud/ubuntu-2004-lts"
gce_ssh_user = "pharaox"
gce_ssh_pub_key_file ="../../resources/test/test_rsa.pub"
gce_ssh_pv_key_file = "../../resources/test/test_rsa"
gce_service_account = "../../resources/service_account/camran_practicasa.json"
static_ip_address = "35.202.33.200"

tags = ["test"]
num = 1
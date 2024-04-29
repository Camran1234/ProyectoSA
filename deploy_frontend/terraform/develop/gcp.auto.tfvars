vm_name = "develop-vm"
region = "us-central1"
zone =   "us-central1-a"
project = "proyecto-sa-421708"
machine_type = "e2-standard-2"
image = "ubuntu-os-cloud/ubuntu-2004-lts"
gce_ssh_user = "pharaox"
gce_ssh_pub_key_file ="../../resources/develop/develop_rsa.pub"
gce_ssh_pv_key_file = "../../resources/develop/develop_rsa"
gce_service_account = "../../resources/service_account/camran_practicasa.json"
static_ip_address = "34.68.158.80"

tags = ["develop"]
num = 1
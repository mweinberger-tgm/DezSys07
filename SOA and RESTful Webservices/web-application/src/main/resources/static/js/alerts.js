bootstrap_alert = function() {};
bootstrap_alert.success = function(message) {
    $('#alert_placeholder').html('<div class="alert alert-success alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+message+'</div>');
};
bootstrap_alert.error = function(message) {
    $('#alert_placeholder').html('<div class="alert alert-danger alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'+message+'</div>');
};
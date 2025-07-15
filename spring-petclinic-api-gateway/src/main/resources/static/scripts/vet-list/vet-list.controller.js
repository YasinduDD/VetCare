"use strict";

angular.module("vetList").controller("VetListController", [
  "$http",
  function ($http) {
    var self = this;

    function loadVets() {
      $http.get("api/vet/vets").then(function (resp) {
        self.vetList = resp.data;
      });
    }
    loadVets();

    self.deleteVet = function (vet) {
      if (!confirm("Are you sure you want to delete this vet?")) return;
      $http
        .delete("api/gateway/vets/" + vet.id)
        .then(function () {
          loadVets();
        })
        .catch(function (error) {
          alert(
            "Failed to delete vet: " +
              (error.data && error.data.message
                ? error.data.message
                : "Unknown error")
          );
        });
    };
  },
]);

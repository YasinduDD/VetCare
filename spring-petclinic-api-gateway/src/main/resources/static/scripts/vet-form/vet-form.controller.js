"use strict";

angular.module("vetForm").controller("VetFormController", [
  "$http",
  "$state",
  function ($http, $state) {
    var self = this;
    self.vet = {};

    self.submit = function () {
      $http
        .post("api/gateway/vets", self.vet)
        .then(function () {
          $state.go("vets");
        })
        .catch(function (error) {
          alert(
            "Failed to add vet: " +
              (error.data && error.data.message
                ? error.data.message
                : "Unknown error")
          );
        });
    };
  },
]);

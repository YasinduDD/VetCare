"use strict";

angular.module("vetForm").controller("VetFormController", [
  "$http",
  "$state",
  "$stateParams",
  function ($http, $state, $stateParams) {
    var self = this;
    self.vet = { specialties: [] };
    self.specialties = [];
    var vetId = $stateParams.vetId;

    // Fetch specialties
    $http.get("api/gateway/vets/specialties").then(function (resp) {
      self.specialties = resp.data;
    });

    // If editing, load vet details
    if (vetId) {
      $http.get("api/vet/vets").then(function (resp) {
        var found = resp.data.find(function (v) {
          return v.id == vetId;
        });
        if (found) {
          self.vet = angular.copy(found);
        }
      });
    }

    self.toggleSpecialty = function (specialty) {
      var idx = self.vet.specialties.findIndex(function (s) {
        return s.id === specialty.id;
      });
      if (idx > -1) {
        self.vet.specialties.splice(idx, 1);
      } else {
        self.vet.specialties.push(specialty);
      }
    };

    self.isSpecialtySelected = function (specialty) {
      return self.vet.specialties.some(function (s) {
        return s.id === specialty.id;
      });
    };

    self.submit = function () {
      var req;
      if (vetId) {
        req = $http.put("api/gateway/vets/" + vetId, self.vet);
      } else {
        req = $http.post("api/gateway/vets", self.vet);
      }
      req
        .then(function () {
          $state.go("vets");
        })
        .catch(function (error) {
          alert(
            "Failed to save vet: " +
              (error.data && error.data.message
                ? error.data.message
                : "Unknown error")
          );
        });
    };
  },
]);

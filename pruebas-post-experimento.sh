#!/bin/bash

MONO="http://libreria-digital-war-env-monolit-env.eba-hdzjpmj8.us-east-2.elasticbeanstalk.com"
MODERN="http://libreria-digital-war-env-modernizada.eba-xxguactp.us-east-2.elasticbeanstalk.com"

echo "=========================================="
echo "   PERFORMANCE TEST - LIBRERÍA DIGITAL    "
echo "=========================================="
echo "Date: $(date)"
echo ""

test_endpoint() {
    local name=$1
    local url=$2
    local endpoint=$3

    echo "Testing $name - $endpoint:"

    for i in 1 2 3 4 5; do
        time_ms=$(curl -s -o /dev/null -w "%{time_total}\n" "${url}${endpoint}" | awk '{printf "%.0f", $1 * 1000}')
        echo "  Test $i: ${time_ms}ms"
        sleep 0.5
    done
    echo ""
}

endpoints=("/" "/clientes" "/productos")

for endpoint in "${endpoints[@]}"; do
    echo "=========================================="
    echo "Endpoint: $endpoint"
    echo "------------------------------------------"

    test_endpoint "MONOLITH  " "$MONO" "$endpoint"
    test_endpoint "MODERNIZED" "$MODERN" "$endpoint"

    echo ""
done

echo "=========================================="
echo "Test completed!"
echo "=========================================="